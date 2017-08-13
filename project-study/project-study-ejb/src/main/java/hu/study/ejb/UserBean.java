package hu.study.ejb;

import hu.study.helper.crypto.PasswordGenerator;
import hu.study.model.dao.TokenDAO;
import hu.study.model.dao.UserDAO;
import hu.study.model.entity.Token;
import hu.study.model.entity.User;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by martin4955 on 2017. 06. 20..
 */
@Stateless(name = "UserEJB")
public class UserBean implements UserBeanIF {

    @PersistenceContext(name = "ProStu")
    protected EntityManager entityManager;

    protected UserDAO userDAO = null;
    protected TokenDAO tokenDAO = null;

    public UserBean() {
    }

    @PostConstruct
    public void setUp() {
        userDAO = new UserDAO(entityManager);
        tokenDAO = new TokenDAO(entityManager);
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User getUserIfValid(String email, String password) throws Exception {
        Optional<User> foundUser = userDAO.find(email);
        if(foundUser.isPresent()) {
            if (PasswordGenerator.check(password, foundUser.get().getPassword())) {
                return foundUser.get();
            } else {
                throw new IllegalStateException("The user has different password");
            }
        } else {
            throw new IllegalStateException("There is no user with such credidentals");
        }
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> foundUser = userDAO.find(email);
        if(foundUser.isPresent()) {
            return foundUser.get();
        } else {
            throw new IllegalStateException("There is no user with such email");
        }
    }

    @Override
    public void registerUser(User user) throws Exception {
        user.setPassword(PasswordGenerator.getSaltedHash(user.getPassword()));
        user.setRegistrationDate(Date.valueOf(LocalDate.now()));
        userDAO.create(user);
    }

    @Override
    public boolean isExistingUser(User user) {
        Optional<User> foundUser = userDAO.find(user.getEmail());
        if(foundUser.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteTokenIfExists(User user) {
        Token token = user.getToken();
        if(token != null) {
            user.setToken(null);
            userDAO.update(user);
            tokenDAO.delete(token);
        }
    }

    @Override
    public Token issueToken(User user) {
        Token token = PasswordGenerator.createToken();
        token.setUser(user);
        user.setToken(token);
        userDAO.update(user);
        return token;
    }

    @Override
    public void modifyUser(User user, String principalEmail) throws Exception {
        User existingUser = getUserByEmail(principalEmail);
        if(!existingUser.getEmail().equals(user.getEmail())) {
            existingUser.setEmail(user.getEmail());
        }
        if(user.getPassword() != null && !PasswordGenerator.check(user.getPassword(), existingUser.getPassword())) {
            existingUser.setPassword(PasswordGenerator.getSaltedHash(user.getPassword()));
        }
        if(user.getDescription() != null && !existingUser.getDescription().equals(user.getDescription())) {
            existingUser.setDescription(user.getDescription());
        }
        if(!existingUser.getFirstName().equals(user.getFirstName())) {
            existingUser.setFirstName(user.getFirstName());
        }
        if(!existingUser.getLastName().equals(user.getLastName())) {
            existingUser.setLastName(user.getLastName());
        }
        userDAO.update(existingUser);
    }
}
