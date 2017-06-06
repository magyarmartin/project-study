package hu.study.model.dao;

import hu.study.model.JPAHibernateTest;
import hu.study.model.entity.Course;
import org.junit.Assert;
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
    public void testFind() {
        Optional<Course> course = courseDAO.find(-1);
        Assert.assertEquals("It should be present", true, course.isPresent());

        course = courseDAO.find(2);
        Assert.assertEquals("It shouldn't be present", false, course.isPresent());
    }

    @Test
    public void testFindByName() {
        Optional<Course> course = courseDAO.find("Coding 101");
        Assert.assertEquals("It should be present", true, course.isPresent());

        course = courseDAO.find("Coding 111");
        Assert.assertEquals("It shouldn't be present", false, course.isPresent());
    }

    @Test
    public void testFildLikeName() {
        List<Course> courses = courseDAO.findLikeName("Something");
        Assert.assertEquals("The list should be empty", 0, courses.size());

        courses = courseDAO.findLikeName("ding");
        Assert.assertEquals(2, courses.size());
    }

    @Test
    public void testCreate() {
        Course course = new Course();
        course.setName("How to make fire");
        courseDAO.create(course);
        Optional<Course> foundCourse = courseDAO.find(course.getId());
        Assert.assertEquals("Previously inserted row should be present", true, foundCourse.isPresent());
        Assert.assertNotNull("Creation date should be filled", foundCourse.get().getCreationDate());

        thrown.expect(IllegalArgumentException.class);
        courseDAO.create(course);
    }

    @Test
    public void testUpdate() {
        final String description = "Some description";
        Course course = courseDAO.find(-1).get();
        course.setDescription(description);
        courseDAO.update(course);

        Course foundCourse = courseDAO.find(course.getId()).get();
        Assert.assertEquals("Description should be foo", description, foundCourse.getDescription());
    }

    @Test
    public void testDelete() {
        Course course = courseDAO.find(-3).get();
        courseDAO.delete(course);
        Optional<Course> foundCourse = courseDAO.find(-3);
        Assert.assertEquals("It shouldn't be present", false, foundCourse.isPresent());
    }
}
