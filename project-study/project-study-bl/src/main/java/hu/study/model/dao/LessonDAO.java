package hu.study.model.dao;

import hu.study.model.entity.Lesson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.Optional;

/**
 * DAO object for {@link Lesson}
 */
public class LessonDAO extends BasicDAO<Lesson> {

    private static final Logger LOG = LogManager.getLogger( LessonDAO.class );

    LessonDAO(EntityManager em) {
        super(em, Lesson.class);
    }

    /**
     * Update the given {@link Lesson}.
     *
     * @param lesson the object to update.
     * @throws IllegalArgumentException when the given object is not exist.
     */
    @Override
    public void update(Lesson lesson) throws IllegalArgumentException {
        Optional<Lesson> les = find(lesson.getId());
        if(les.isPresent()) {
            em.getTransaction().begin();
            em.merge(lesson);
            em.getTransaction().commit();
            LOG.info("Lesson with id: " + lesson.getId() + "updated");
        } else {
            throw new IllegalArgumentException("The given section is not exist in the database");
        }
    }

    @Override
    public void create(Lesson lesson) {
        lesson.setCreationDate(new Date(new java.util.Date().getTime()));
        super.create(lesson);
    }
}
