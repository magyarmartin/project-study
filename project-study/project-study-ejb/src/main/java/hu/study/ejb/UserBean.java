package hu.study.ejb;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.study.helper.crypto.PasswordGenerator;
import hu.study.model.dao.TokenDAO;
import hu.study.model.dao.UserDAO;
import hu.study.model.entity.Course;
import hu.study.model.entity.Token;
import hu.study.model.entity.User;
import hu.study.model.exception.AuthenticationException;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

/**
 * Created by martin4955 on 2017. 06. 20..
 */
@Log4j2
@Stateless( name = "UserEJB" )
public class UserBean implements UserBeanIF {

    @PersistenceContext( name = "ProStu" )
    protected EntityManager entityManager;

    protected UserDAO userDAO;
    protected TokenDAO tokenDAO;

    public UserBean() {}

    @PostConstruct
    public void setUp() {
        userDAO = new UserDAO(entityManager);
        tokenDAO = new TokenDAO(entityManager);
    }

    public void setEntityManager( final EntityManager entityManager ) {
        this.entityManager = entityManager;
    }

    @Override
    public User getUserIfValid( @NonNull final String email, @NonNull final String password ) throws Exception {
        Optional<User> foundUser = userDAO.find(email);
        if ( foundUser.isPresent() ) {
            if ( PasswordGenerator.check(password, foundUser.get().getPassword()) ) {
                return foundUser.get();
            } else {
                throw new AuthenticationException("The user has different password");
            }
        } else {
            throw new AuthenticationException("There is no user with such credidentals");
        }
    }

    @Override
    public User getUserByEmail( @NonNull final String email ) {
        Optional<User> foundUser = userDAO.find(email);
        if ( foundUser.isPresent() ) {
            return foundUser.get();
        } else {
            throw new IllegalStateException("There is no user with such email");
        }
    }

    @Override
    public void registerUser( @NonNull final User user ) throws Exception {
        user.setPassword(PasswordGenerator.getSaltedHash(user.getPassword()));
        user.setRegistrationDate(Date.valueOf(LocalDate.now()));
        userDAO.create(user);
        log.info("User successfully registered. Email: " + user.getEmail() + " id: " + user.getId());
    }

    @Override
    public boolean isExistingUser( @NonNull final User user ) {
        Optional<User> foundUser = userDAO.find(user.getEmail());
        if ( foundUser.isPresent() ) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteUser( @NonNull final String email ) {
        User user = getUserByEmail(email);
        userDAO.delete(user);
        log.info("User successfully deleted: ", user);
    }

    @Override
    public void deleteTokenIfExists( @NonNull final User user ) {
        Token token = user.getToken();
        if ( token != null ) {
            user.setToken(null);
            userDAO.update(user);
            tokenDAO.delete(token);
            log.info("Token deleted: ", token);
        }
    }

    @Override
    public Token issueToken( @NonNull final User user ) {
        Token token = PasswordGenerator.createToken();
        token.setUser(user);
        user.setToken(token);
        userDAO.update(user);
        return token;
    }

    @Override
    public void modifyUser( @NonNull final User user, @NonNull final String principalEmail ) throws Exception {
        User existingUser = getUserByEmail(principalEmail);
        String email = user.getEmail();
        if ( email != null && !email.isEmpty() && !existingUser.getEmail().equals(email) ) {
            existingUser.setEmail(user.getEmail());
        }
        String password = user.getPassword();
        if ( password != null  && !password.isEmpty() && !PasswordGenerator.check(password, existingUser.getPassword()) ) {
            existingUser.setPassword(PasswordGenerator.getSaltedHash(password));
        }
        String description = user.getDescription();
        if ( description != null && !description.isEmpty() && !existingUser.getDescription().equals(description) ) {
            existingUser.setDescription(description);
        }
        String firstName = user.getFirstName();
        if ( firstName != null && !firstName.isEmpty() && !existingUser.getFirstName().equals(firstName) ) {
            existingUser.setFirstName(firstName);
        }
        String lastName = user.getLastName();
        if ( lastName != null && !lastName.isEmpty() && !existingUser.getLastName().equals(lastName) ) {
            existingUser.setLastName(lastName);
        }
        userDAO.update(existingUser);
        log.info("User successfully modified. UserId: ", user.getId());
    }

    @Override
    public void signUpCourse( @NonNull final String email, @NonNull final Course course ) {
        User user = getUserByEmail(email);
        if ( user.getStartedCourses().contains(course) ) {
            throw new IllegalArgumentException("The user already signed up for the given course");
        }
        user.getStartedCourses().add(course);
        userDAO.update(user);
    }
}
