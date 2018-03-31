package hu.study.model.dao;

import java.util.Optional;

public interface DAO<T> {

    /**
     * Returns an {@link Optional} with the searched {@link T}.
     *
     * @param id the id of the searched object.
     * @return and {@link Optional} containing the requested object, or null.
     */
    @SuppressWarnings( "Since15" )
    Optional<T> find( int id );

    /**
     * Update the given {@link T}.
     *
     * @param object the object to update.
     * @throws IllegalArgumentException when the given object is not exist.
     */
    void update( T object ) throws IllegalArgumentException;

    /**
     * Create the given object in the Database.
     *
     * @param object the object to create.
     * @throws IllegalArgumentException when the given object is exist.
     */
    void create( T object );

    /**
     * Delete the given object.
     *
     * @param object
     */
    void delete( T object );

}
