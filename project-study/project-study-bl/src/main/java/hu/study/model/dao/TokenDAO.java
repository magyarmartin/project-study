package hu.study.model.dao;

import hu.study.model.entity.Token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Optional;

/**
 * Created by martin4955 on 2017. 08. 12..
 */
public class TokenDAO extends BasicDAO<Token>{

    private static final Logger LOG = LogManager.getLogger( UserDAO.class );

    public TokenDAO(EntityManager em) {
        super(em, Token.class);
    }

    /**
     * Returns an {@link Optional} with the searched {@link Token}.
     *
     * @param tokenStr the email of the user.
     * @return and {@link Optional} containing the requested user, or null.
     */
    public Optional<Token> find(String tokenStr) {
        Query query = em.createNamedQuery("Token.findByToken");
        query.setParameter("token", tokenStr);
        try {
            Token token = (Token) query.getSingleResult();
            return Optional.ofNullable(token);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    /**
     * Update the given {@link Token}.
     *
     * @param token the object to update.
     * @throws IllegalArgumentException when the given object is not exist.
     */
    @Override
    public void update(Token token) throws IllegalArgumentException {
        if(isUserExist(token)) {
            em.merge(token);
            LOG.info("Token with tokenstring: " + token.getToken() + "updated");
        } else {
            throw new IllegalArgumentException("The given token is not exist in the database");
        }
    }

    private boolean isUserExist(Token token) {
        Optional<Token> foundToken = find(token.getId());
        return foundToken.isPresent();
    }
}
