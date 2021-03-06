package hu.study.model.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import hu.study.model.entity.Token;
import lombok.NonNull;
import lombok.val;
import lombok.extern.log4j.Log4j2;

/**
 * Created by martin4955 on 2017. 08. 12..
 */
@Log4j2
public class TokenDAO extends BasicDAO<Token> {

    public TokenDAO( final EntityManager em ) {
        super(em, Token.class);
    }

    /**
     * Returns an {@link Optional} with the searched {@link Token}.
     *
     * @param tokenStr the email of the user.
     * @return and {@link Optional} containing the requested user, or null.
     */
    public Optional<Token> find( @NonNull final String tokenStr ) {
        val query = em.createNamedQuery("Token.findByToken");
        query.setParameter("token", tokenStr);
        try {
            val token = (Token) query.getSingleResult();
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
    public void update( @NonNull final Token token ) throws IllegalArgumentException {
        if ( isUserExist(token) ) {
            em.merge(token);
            log.info("Token with tokenstring: " + token.getToken() + "updated");
        } else {
            throw new IllegalArgumentException("The given token is not exist in the database");
        }
    }

    private boolean isUserExist( @NonNull final Token token ) {
        return find(token.getId()).isPresent();
    }
}
