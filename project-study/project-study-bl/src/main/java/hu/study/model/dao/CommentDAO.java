package hu.study.model.dao;

import hu.study.model.entity.Comment;
import hu.study.model.entity.Section;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.Optional;

public class CommentDAO extends BasicDAO<Comment>{

    private static final Logger LOG = LogManager.getLogger( CommentDAO.class );

    public CommentDAO(EntityManager em) {
        super(em, Comment.class);
    }

    /**
     * Update the given {@link Comment}.
     *
     * @param comment the comment to update.
     * @throws IllegalArgumentException when the given comment is not exist.
     */
    @Override
    public void update(Comment comment) throws IllegalArgumentException {
        Optional<Comment> com = find(comment.getId());
        if(com.isPresent()) {
            em.getTransaction().begin();
            em.merge(comment);
            em.getTransaction().commit();
            LOG.info("Section with id: " + comment.getId() + "updated");
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
    public void create(Comment comment) {
        comment.setCreationDate(new Date(new java.util.Date().getTime()));
        super.create(comment);
    }

}
