package hu.study.ejb;

import hu.study.model.dao.TokenDAO;
import hu.study.model.entity.Token;
import hu.study.model.entity.User;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotAuthorizedException;
import java.util.Date;
import java.util.Optional;

/**
 * Created by martin4955 on 2017. 08. 12..
 */
@NoArgsConstructor
@Log4j2
@Stateless(name = "TokenEJB")
public class TokenBean implements TokenBeanIF {

    @PersistenceContext(name = "ProStu")
    protected EntityManager entityManager;

    TokenDAO dao;

    @PostConstruct
    public void setUp() {
        dao = new TokenDAO( entityManager );
    }

    public Token getToken( @NonNull String tokenStr ) {
        Optional<Token> token = dao.find( tokenStr );
        if( token.isPresent() && token.get().getExpirationDate().after( new Date() ) ) {
            return token.get();
        } else {
            log.warn( "The given token is not valid: " + token );
            throw new NotAuthorizedException( "The token is not valid" );
        }
    }

    public boolean isValidToken( @NonNull String tokenStr ) {
        Optional<Token> token = dao.find( tokenStr );
        if( token.isPresent() && token.get().getExpirationDate().before( new Date() ) ) {
            return true;
        } else {
            return false;
        }
    }

}
