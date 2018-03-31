package hu.study.model.dao;

import java.sql.Date;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import hu.study.model.entity.User;
import lombok.NonNull;
import lombok.val;
import lombok.extern.log4j.Log4j2;

/**
 * DAO object for {@link User}
 */
@Log4j2
public class UserDAO extends BasicDAO<User> {

    public UserDAO( final EntityManager em ) {
        super(em, User.class);
    }

    /**
     * Returns an {@link Optional} with the searched {@link User}.
     *
     * @param email the email of the user.
     * @return and {@link Optional} containing the requested user, or null.
     */
    public Optional<User> find( @NonNull final String email ) {
        val query = em.createNamedQuery("User.findByEmail");
        query.setParameter("email", email);
        try {
            User user = (User) query.getSingleResult();
            return Optional.ofNullable(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    /**
     * Update the given {@link User}.
     *
     * @param user the user to update.
     * @throws IllegalArgumentException when the given User is not exist.
     */
    @Override
    public void update( @NonNull final User user ) throws IllegalArgumentException {
        if ( isUserExist(user) ) {
            em.merge(user);
            log.info("User with email: " + user.getEmail() + "updated");
        } else {
            throw new IllegalArgumentException("The given user is not exist in the database");
        }
    }

    /**
     * Create the given user in the Database.
     *
     * @param user the user to create.
     * @throws IllegalArgumentException when the given User is exist.
     */
    @Override
    public void create( @NonNull final User user ) throws IllegalArgumentException {
        if ( !isUserExist(user) ) {
            user.setRegistrationDate(new Date(new java.util.Date().getTime()));
            super.create(user);
            log.info("User created with email: " + user.getEmail());
        } else {
            throw new IllegalArgumentException("The user is already exist in the database");
        }
    }

    private boolean isUserExist( @NonNull final User user ) {
        if ( user.getEmail() != null ) {
            return find(user.getEmail()).isPresent();
        }
        return false;
    }
}
