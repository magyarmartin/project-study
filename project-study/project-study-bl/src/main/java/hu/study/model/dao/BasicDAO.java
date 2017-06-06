package hu.study.model.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.Optional;

/**
 * Created by martin4955 on 2017. 06. 06..
 */
public abstract class BasicDAO<T> implements DAO<T>{


    private static final Logger LOG = LogManager.getLogger( CommentDAO.class );
    protected EntityManager em;
    protected Class entityClass;

    public BasicDAO(EntityManager em, Class<T> entityClass) {
        super();
        this.em = em;
        this.entityClass = entityClass;
    }

    /**
     * Returns an {@link Optional} with the searched {@link T}.
     *
     * @param id the id of the searched object.
     * @return and {@link Optional} containing the requested object, or null.
     */
    @Override
    public Optional<T> find(int id) {
        T t = (T) em.find(entityClass, id);
        return Optional.ofNullable(t);
    }

    /**
     * Create the given object in the Database.
     *
     * @param object the object to create.
     * @throws IllegalArgumentException when the given object is exist.
     */
    @Override
    public void create(T object) {
        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
        LOG.info("Object created", object);
    }

    /**
     * Delete the given object.
     *
     * @param object
     */
    @Override
    public void delete(T object) {
        em.remove(object);
    }
}
