package hu.study.model.dao;

import hu.study.model.entity.Lesson;
import hu.study.model.entity.Rating;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.Optional;

/**
 * Created by martin4955 on 2017. 06. 06..
 */
public class RatingDAO extends BasicDAO<Rating> {

    private static final Logger LOG = LogManager.getLogger( LessonDAO.class );

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
    public void update(Rating rating) throws IllegalArgumentException {
        Optional<Rating> rat = find(rating.getId());
        if(rat.isPresent()) {
            em.getTransaction().begin();
            em.merge(rating);
            em.getTransaction().commit();
            LOG.info("Rating with id: " + rating.getId() + "updated");
        } else {
            throw new IllegalArgumentException("The given section is not exist in the database");
        }
    }

    @Override
    public void create(Rating rating) {
        rating.setCreationDate(new Date(new java.util.Date().getTime()));
        super.create(rating);
    }
}
