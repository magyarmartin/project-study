package hu.study.model.dao;

import java.util.Optional;
import java.sql.Date;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import hu.study.model.entity.User;

/**
 * DAO object for {@link User}
 */
public class UserDAO extends BasicDAO<User>{

	private static final Logger LOG = LogManager.getLogger( UserDAO.class );
	private static final String USER_BY_NAME = "SELECT COUNT(u) FROM User u WHERE ";

	public UserDAO(EntityManager em) {
		super(em, User.class);
	}

    /**
     * Returns an {@link Optional} with the searched {@link User}.
     *
     * @param email the email of the user.
     * @return and {@link Optional} containing the requested user, or null.
     */
	public Optional<User> find(String email) {
		Query query = em.createNamedQuery("User.findByEmail");
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
	public void update(User user) throws IllegalArgumentException {
		if(isUserExist(user)) {
			em.getTransaction().begin();
			em.merge(user);
			em.getTransaction().commit();
			LOG.info("User with email: " + user.getEmail() + "updated");
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
	public void create(User user) throws IllegalArgumentException {
		if(!isUserExist(user)) {
			user.setRegistrationDate(new Date(new java.util.Date().getTime()));
			super.create(user);
			LOG.info("User created with email: " + user.getEmail());
		} else {
			throw new IllegalArgumentException("The user is already exist in the database");
		}
	}
	
	private boolean isUserExist(User user) {
		Optional<User> foundUser = find(user.getEmail());
		return foundUser.isPresent();
	}
}
