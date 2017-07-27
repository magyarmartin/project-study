package hu.study.ejb;

import hu.study.helper.crypto.PasswordGenerator;
import hu.study.model.dao.UserDAO;
import hu.study.model.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

/**
 * Created by martin4955 on 2017. 06. 20..
 */
@Stateless(name = "UserEJB")
public class UserBean implements UserBeanIF {

    @PersistenceContext(name = "ProStu")
    protected EntityManager entityManager;

    UserDAO dao = new UserDAO(entityManager);

    public UserBean() {
    }

    public void validatingUser(User user) throws Exception {
        dao = new UserDAO(entityManager);
        Optional<User> foundUser = dao.find(user.getEmail());
        if(foundUser.isPresent()) {
            if (PasswordGenerator.check(user.getPassword(), foundUser.get().getPassword())) {
                return;
            }
        }
        throw new IllegalStateException("There is no user with such credidentals");
    }

    public boolean registerUser(User user) {
        return true;
    }

    public String issueToken(String username) {
        return "asd";
    }
}
