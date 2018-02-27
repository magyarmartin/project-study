package hu.study.model.dao;

import hu.study.model.entity.Lesson;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.Optional;

/**
 * DAO object for {@link Lesson}
 */
@Log4j2
public class LessonDAO extends BasicDAO<Lesson> {

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
    public void update(@NonNull Lesson lesson) throws IllegalArgumentException {
        val les = find(lesson.getId());
        if(les.isPresent()) {
            em.getTransaction().begin();
            em.merge(lesson);
            em.getTransaction().commit();
            log.info("Lesson with id: " + lesson.getId() + "updated");
        } else {
            throw new IllegalArgumentException("The given section is not exist in the database");
        }
    }

    @Override
    public void create(@NonNull Lesson lesson) {
        lesson.setCreationDate(new Date(new java.util.Date().getTime()));
        super.create(lesson);
    }
}
