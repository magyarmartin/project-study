package hu.study.model.dao;

import hu.study.model.JPAHibernateTest;
import hu.study.model.entity.Course;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.Optional;

public class CourseDAOTest extends JPAHibernateTest {
    private static CourseDAO courseDAO;

    @BeforeClass
    public static void initDAO() {
        courseDAO = new CourseDAO(em);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldPresentIfIdExists() {
        Optional<Course> course = courseDAO.find(-1);
        assertThat(course.isPresent(), is(true));
    }

    @Test
    public void shouldNotPresentIfIdNotExists() {
        Optional<Course> course = courseDAO.find(2);
        assertThat(course.isPresent(), is(false));
    }

    @Test
    public void shouldPresentIfNameExists() {
        Optional<Course> course = courseDAO.find("Coding 101");
        assertThat(course.isPresent(), is(true));
    }

    @Test
    public void shouldNotPresentIfNameNotExists() {
        Optional<Course> course = courseDAO.find("Coding 111");
        assertThat(course.isPresent(), is(false));
    }

    @Test
    public void shouldHaveSizeGreaterThanZeroIfThereAreCoursesWithNameLikeTheParameter() {
        List<Course> courses = courseDAO.findLikeName("Something");
        assertThat(courses.size(), is(0));

        courses = courseDAO.findLikeName("ding");
        assertThat(courses.size(), greaterThan(0));
    }

    @Test
    public void sholdBeInTheDBIfCreatedAndFilledWithCreationDate() {
        Course course = new Course();
        course.setName("How to make fire");
        courseDAO.create(course);
        Optional<Course> foundCourse = courseDAO.find(course.getId());
        assertThat(foundCourse.isPresent(), is(true));
        assertThat(foundCourse.get().getCreationDate(), is(notNullValue()));

        thrown.expect(IllegalArgumentException.class);
        courseDAO.create(course);
    }

    @Test
    public void shouldThrowExceptionIfTheNameIfAlreadyExists() {
        Course course = new Course();
        course.setName("Coding 111");
        courseDAO.create(course);

        thrown.expect(IllegalArgumentException.class);
        courseDAO.create(course);
    }

    @Test
    public void shouldUpdateTheGivenCourse() {
        final String description = "Some description";
        Course course = courseDAO.find(-1).get();
        course.setDescription(description);
        courseDAO.update(course);

        Course foundCourse = courseDAO.find(course.getId()).get();
        assertThat(foundCourse.getDescription(), is(equalTo(description)));
    }

    @Test
    public void shouldThrowExceptionIfTheCourseIsNotAlreadyExists() {
        thrown.expect(IllegalArgumentException.class);
        Course course = new Course();
        courseDAO.update(course);
    }

    @Test
    public void shouldNotPresentAfterDelete() {
        Course course = courseDAO.find(-3).get();
        courseDAO.delete(course);
        Optional<Course> foundCourse = courseDAO.find(-3);
        assertThat(foundCourse.isPresent(), is(false));
    }
}
