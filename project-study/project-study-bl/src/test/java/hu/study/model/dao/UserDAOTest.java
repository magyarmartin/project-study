package hu.study.model.dao;

import hu.study.model.JPAHibernateTest;
import hu.study.model.entity.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class UserDAOTest extends JPAHibernateTest {

    private static UserDAO userDAO;

    @BeforeClass
    public static void initDAO() {
        userDAO = new UserDAO(em);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldPresentIfIdExists() {
        Optional<User> user = userDAO.find(-1);
        assertThat(user.isPresent(), is(true));
    }

    @Test
    public void shouldNotPresentIfIdNotExists() {
        Optional<User> user = userDAO.find(2);
        assertThat(user.isPresent(), is(false));
    }

    @Test
    public void shouldPresentIfEmailExists() {
        Optional<User> user = userDAO.find("foo@bar.com");
        assertThat(user.isPresent(), is(true));
    }

    @Test
    public void shouldNotPresentIfEmailNotExists() {
        Optional<User> user = userDAO.find("fooo@foo.com");
        assertThat(user.isPresent(), is(false));
    }

    @Test
    public void shouldBeInTheDBIfCreatedAndFilledWithCreationDate() {
        User user = new User();
        user.setEmail("a@b.c");
        user.setPassword("p");
        user.setInstructor(true);
        userDAO.create(user);
        Optional<User> foundUser = userDAO.find(user.getId());
        assertThat(foundUser.isPresent(), is(true));
        assertThat(foundUser.get().getRegistrationDate(), is(notNullValue()));
    }

    @Test
    public void shouldThrowExceptionIfTheEmailIfAlreadyExists() {
        User user = new User();
        user.setEmail("foo@bar.com");

        thrown.expect(IllegalArgumentException.class);
        userDAO.create(user);
    }

    @Test
    public void shouldUpdateTheGivenUser() {
        final String firstName = "Foo";
        User user = userDAO.find(-1).get();
        user.setFirstName(firstName);
        userDAO.update(user);

        User foundUser = userDAO.find(user.getId()).get();
        assertThat(foundUser.getFirstName(), is(equalTo(firstName)));
    }

    @Test
    public void shouldThrowExceptionIfTheUserIsNotAlreadyExists() {
        thrown.expect(IllegalArgumentException.class);

        User user = new User();
        userDAO.update(user);
    }

    @Test
    public void shouldNotPresentAfterDelete() {
        User user = userDAO.find(-2).get();
        userDAO.delete(user);
        Optional<User> foundUser = userDAO.find(-2);
        assertThat(foundUser.isPresent(), is(false));
    }

}
