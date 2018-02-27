package hu.study.model.dao;

import hu.study.model.entity.Token;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Optional;

/**
 * Created by martin4955 on 2017. 08. 12..
 */
@Log4j2
public class TokenDAO extends BasicDAO<Token> {

    public TokenDAO(EntityManager em) {
        super(em, Token.class);
    }

    /**
     * Returns an {@link Optional} with the searched {@link Token}.
     *
     * @param tokenStr the email of the user.
     * @return and {@link Optional} containing the requested user, or null.
     */
    public Optional<Token> find(@NonNull String tokenStr) {
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
    public void update(@NonNull Token token) throws IllegalArgumentException {
        if (isUserExist(token)) {
            em.merge(token);
            log.info("Token with tokenstring: " + token.getToken() + "updated");
        } else {
            throw new IllegalArgumentException("The given token is not exist in the database");
        }
    }

    private boolean isUserExist(@NonNull Token token) {
        return find(token.getId()).isPresent();
    }
}
