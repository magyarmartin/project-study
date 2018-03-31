package hu.study.ejb;

import java.util.Date;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotAuthorizedException;

import hu.study.model.dao.TokenDAO;
import hu.study.model.entity.Token;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

/**
 * Created by martin4955 on 2017. 08. 12..
 */
@NoArgsConstructor
@Log4j2
@Stateless( name = "TokenEJB" )
public class TokenBean implements TokenBeanIF {

    @PersistenceContext( name = "ProStu" )
    protected EntityManager entityManager;

    TokenDAO dao;

    @PostConstruct
    public void setUp() {
        dao = new TokenDAO(entityManager);
    }

    @Override
    public Token getToken( @NonNull final String tokenStr ) {
        Optional<Token> token = dao.find(tokenStr);
        if ( token.isPresent() && token.get().getExpirationDate().after(new Date()) ) {
            return token.get();
        } else {
            log.warn("The given token is not valid: " + token);
            throw new NotAuthorizedException("The token is not valid");
        }
    }

    @Override
    public boolean isValidToken( @NonNull final String tokenStr ) {
        Optional<Token> token = dao.find(tokenStr);
        if ( token.isPresent() && token.get().getExpirationDate().before(new Date()) ) {
            return true;
        } else {
            return false;
        }
    }

}
