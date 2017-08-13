package hu.study.ejb;

import hu.study.model.dao.TokenDAO;
import hu.study.model.entity.Token;
import hu.study.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotAuthorizedException;
import java.util.Date;
import java.util.Optional;

/**
 * Created by martin4955 on 2017. 08. 12..
 */
@Stateless(name = "TokenEJB")
public class TokenBean implements TokenBeanIF{

    private static final Logger LOG = LogManager.getLogger( TokenBean.class );

    @PersistenceContext(name = "ProStu")
    protected EntityManager entityManager;

    TokenDAO dao = null;

    public TokenBean() {
    }

    @PostConstruct
    public void setUp() {
        dao = new TokenDAO(entityManager);
    }

    public Token getToken(String tokenStr) {
        Optional<Token> token = dao.find(tokenStr);
        if(token.isPresent() && token.get().getExpirationDate().after(new Date())) {
            return token.get();
        } else {
            LOG.warn("The given token is not valid: " + token);
            throw new NotAuthorizedException("The token is not valid");
        }
    }

    public boolean isValidToken(String tokenStr) {
        Optional<Token> token = dao.find(tokenStr);
        if(token.isPresent() && token.get().getExpirationDate().before(new Date())) {
            return true;
        } else {
            return false;
        }
    }

}
