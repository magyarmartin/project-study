package hu.study.model.dao;

import java.util.Optional;

import javax.persistence.EntityManager;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

/**
 * Created by martin4955 on 2017. 06. 06..
 */
@Log4j2
public abstract class BasicDAO<T> implements DAO<T> {

    protected EntityManager em;
    protected Class entityClass;

    public BasicDAO( @NonNull final EntityManager em, @NonNull final Class<T> entityClass ) {
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
    public Optional<T> find( final int id ) {
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
    public void create( @NonNull final T object ) {
        em.persist(object);
        log.info("Object created", object);
    }

    /**
     * Delete the given object.
     *
     * @param object
     */
    @Override
    public void delete( @NonNull final T object ) {
        em.remove(em.contains(object) ? object : em.merge(object));
    }
}
