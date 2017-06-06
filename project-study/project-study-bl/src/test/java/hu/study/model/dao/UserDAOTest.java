package hu.study.model.dao;

import hu.study.model.JPAHibernateTest;
import hu.study.model.entity.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Optional;

public class UserDAOTest extends JPAHibernateTest {

    private static UserDAO userDAO;

    @BeforeClass
    public static void initDAO() {
        userDAO = new UserDAO(em);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testFind() {
        Optional<User> user = userDAO.find(-1);
        Assert.assertEquals("It should be present", true, user.isPresent());

        user = userDAO.find(2);
        Assert.assertEquals("It shouldn't be present", false, user.isPresent());
    }

    @Test
    public void testFindByEmail() {
        Optional<User> user = userDAO.find("foo@bar.com");
        Assert.assertEquals("It should be present", true, user.isPresent());

        user = userDAO.find("fooo@foo.com");
        Assert.assertEquals("It shouldn't be present", false, user.isPresent());
    }

    @Test
    public void testCreate() {
        User user = new User();
        user.setEmail("a@b.c");
        user.setPassword("p");
        user.setInstructor(true);
        userDAO.create(user);
        Optional<User> foundUser = userDAO.find(user.getId());
        Assert.assertEquals("Previously inserted row should be present", true, foundUser.isPresent());
        Assert.assertNotNull("Registration date should be filled", foundUser.get().getRegistrationDate());

        thrown.expect(IllegalArgumentException.class);
        userDAO.create(user);
    }

    @Test
    public void testUpdate() {
        final String firstName = "Foo";
        User user = userDAO.find(-1).get();
        user.setFirstName(firstName);
        userDAO.update(user);

        User foundUser = userDAO.find(user.getId()).get();
        Assert.assertEquals("Users name should be foo", firstName, foundUser.getFirstName());
    }

    @Test
    public void testDelete() {
        User user = userDAO.find(-2).get();
        userDAO.delete(user);
        Optional<User> foundUser = userDAO.find(-2);
        Assert.assertEquals("It shouldn't be present", false, foundUser.isPresent());
    }

}
