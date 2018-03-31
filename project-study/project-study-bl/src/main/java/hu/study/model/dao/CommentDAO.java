package hu.study.model.dao;

import java.sql.Date;

import javax.persistence.EntityManager;

import hu.study.model.entity.Comment;
import lombok.NonNull;
import lombok.val;
import lombok.extern.log4j.Log4j2;

/**
 * DAO object for {@link Comment}
 */
@Log4j2
public class CommentDAO extends BasicDAO<Comment> {

    public CommentDAO( final EntityManager em ) {
        super(em, Comment.class);
    }

    /**
     * Update the given {@link Comment}.
     *
     * @param comment the comment to update.
     * @throws IllegalArgumentException when the given comment is not exist.
     */
    @Override
    public void update( @NonNull final Comment comment ) throws IllegalArgumentException {
        val com = find(comment.getId());
        if ( com.isPresent() ) {
            em.getTransaction().begin();
            em.merge(comment);
            em.getTransaction().commit();
            log.info("Section with id: " + comment.getId() + "updated");
        } else {
            throw new IllegalArgumentException("The given section is not exist in the database");
        }
    }

    /**
     * Create the given comment in the Database.
     *
     * @param comment the comment to create.
     * @throws IllegalArgumentException when the given comment is exist.
     */
    @Override
    public void create( @NonNull final Comment comment ) {
        comment.setCreationDate(new Date(new java.util.Date().getTime()));
        super.create(comment);
    }

}
