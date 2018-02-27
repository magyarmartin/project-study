package hu.study.model.dao;

import hu.study.model.entity.Lesson;
import hu.study.model.entity.Rating;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.Optional;

/**
 * DAO object for {@link Rating}
 */
@Log4j2
public class RatingDAO extends BasicDAO<Rating> {

    RatingDAO(EntityManager em) {
        super(em, Rating.class);
    }

    /**
     * Update the given {@link Rating}.
     *
     * @param rating the rating to update.
     * @throws IllegalArgumentException when the given rating is not exist.
     */
    @Override
    public void update(@NonNull Rating rating) throws IllegalArgumentException {
        val rat = find(rating.getId());
        if(rat.isPresent()) {
            em.getTransaction().begin();
            em.merge(rating);
            em.getTransaction().commit();
            log.info("Rating with id: " + rating.getId() + "updated");
        } else {
            throw new IllegalArgumentException("The given section is not exist in the database");
        }
    }

    @Override
    public void create(@NonNull Rating rating) {
        rating.setCreationDate(new Date(new java.util.Date().getTime()));
        super.create(rating);
    }
}
