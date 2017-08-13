package hu.study.model.dao;

import hu.study.model.JPAHibernateTest;
import hu.study.model.entity.Token;
import hu.study.model.entity.User;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by martin4955 on 2017. 08. 13..
 */
public class TokenDAOTest extends JPAHibernateTest {

    private static TokenDAO tokenDAO;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void initDAO() {
        tokenDAO = new TokenDAO(em);
    }

    @Test
    public void shouldPresentIfIdExists() {
        Optional<Token> token = tokenDAO.find(-2);
        assertThat(token.isPresent(), is(true));
    }

    @Test
    public void shouldNotPresentIfIdNotExists() {
        Optional<Token> token = tokenDAO.find(2);
        assertThat(token.isPresent(), is(false));
    }

    @Test
    public void shouldPresentIfTokenExists() {
        Optional<Token> token = tokenDAO.find("asdf1234");
        assertThat(token.isPresent(), is(true));
    }

    @Test
    public void shouldNotPresentIfTokenNotExists() {
        Optional<Token> token = tokenDAO.find("asdf4321");
        assertThat(token.isPresent(), is(false));
    }

    @Test
    public void shouldBeInTheDBIfCreated() {
        Token token = new Token();
        token.setUser(new User());
        token.setExpirationDate(new Date(new java.util.Date().getTime()));
        token.setToken("asd");
        tokenDAO.create(token);
        Optional<Token> foundToken = tokenDAO.find(token.getId());
        assertThat(foundToken.isPresent(), is(true));
    }

    @Test
    public void shouldUpdateTheGivenUser() {
        final String tokenStr = "asdasdasd";
        Token token = tokenDAO.find(-2).get();
        token.setToken(tokenStr);
        tokenDAO.update(token);

        Token foundToken = tokenDAO.find(-2).get();
        assertThat(foundToken.getToken(), is(equalTo(tokenStr)));
    }

    @Test
    public void shouldThrowExceptionIfTheTokenIsNotAlreadyExists() {
        thrown.expect(IllegalArgumentException.class);

        Token token = new Token();
        token.setId(123333);
        tokenDAO.update(token);
    }

    @Test
    public void shouldNotPresentAfterDelete() {
        Token token = tokenDAO.find(1).get();
        tokenDAO.delete(token);
        Optional<Token> foundToken = tokenDAO.find(1);
        assertThat(foundToken.isPresent(), is(false));
    }
}
