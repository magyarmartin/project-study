package hu.study.model.dao;

import hu.study.model.JPAHibernateTest;
import hu.study.model.entity.Rating;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by martin4955 on 2017. 06. 07..
 */
public class RatingDAOTest extends JPAHibernateTest {
    private static RatingDAO ratingDAO;

    @BeforeClass
    public static void initDAO() {
        ratingDAO = new RatingDAO(em);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldPresentIfIdExists() {
        Optional<Rating> rating = ratingDAO.find(-1);
        assertThat(rating.isPresent(), is(true));
    }

    @Test
    public void shouldNotPresentIfIdNotExists() {
        Optional<Rating> rating = ratingDAO.find(2);
        assertThat(rating.isPresent(), is(false));
    }

    @Test
    public void shouldBeInTheDBIfCreatedAndFilledWithCreationDate() {
        Rating rating = new Rating();
        rating.setDescription("asd");
        ratingDAO.create(rating);
        Optional<Rating> foundRating = ratingDAO.find(rating.getId());
        assertThat(foundRating.isPresent(), is(true));
        assertThat(foundRating.get().getCreationDate(), is(notNullValue()));
    }

    @Test
    public void shouldUpdateTheGivenComment() {
        final String description = "Some comment";
        Rating rating = ratingDAO.find(-1).get();
        rating.setDescription(description);
        ratingDAO.update(rating);

        Rating foundRating = ratingDAO.find(rating.getId()).get();
        assertThat(foundRating.getDescription(), is(equalTo(description)));
    }

    @Test
    public void shouldThrowExceptionIfTheCourseIsNotAlreadyExists() {
        thrown.expect(NullPointerException.class);
        Rating rating = new Rating();
        ratingDAO.update(rating);
    }

    @Test
    public void shouldNotPresentAfterDelete() {
        Rating rating = ratingDAO.find(-2).get();
        ratingDAO.delete(rating);
        Optional<Rating> foundRating = ratingDAO.find(-2);
        assertThat(foundRating.isPresent(), is(false));
    }
}
