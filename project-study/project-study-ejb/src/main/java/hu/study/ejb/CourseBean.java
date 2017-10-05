package hu.study.ejb;

import hu.study.model.dao.CourseDAO;
import hu.study.model.entity.Course;
import hu.study.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Created by martin4955 on 2017. 09. 09..
 */
@Stateless(name = "CourseEJB")
public class CourseBean implements CourseBeanIF {

    private static final Logger LOG = LogManager.getLogger( CourseBean.class );

    @PersistenceContext(name = "ProStu")
    protected EntityManager entityManager;

    @EJB protected UserBeanIF userBean;
    protected CourseDAO courseDAO;

    @PostConstruct
    public void setUp() {
        courseDAO = new CourseDAO(entityManager);
    }

    public CourseBean() {}

    @Override
    public Course getCourse(String name) {
        Optional<Course> course = courseDAO.find(name);
        if(course.isPresent()) {
            return course.get();
        }
        return null;
    }

    @Override
    public List<Course> getCourses(String courseName, int limit, String ordering, String order) {
        return courseDAO.findLikeName(courseName, ordering, order, limit);
    }

    @Override
    public void createCourse(Course course) {

    }

    @Override
    public void deleteCourse(String courseName) {
        Course existingCourse = getCourse(courseName);
        courseDAO.delete(existingCourse);
        LOG.info("Successfully deleted the course: ", existingCourse);
    }

    @Override
    public void modifyCourse(String courseName, Course course) {
        Course existingCourse = getCourse(courseName);
        if(!course.getName().equals(existingCourse.getName())) {
            existingCourse.setName(course.getName());
        }
        if(!course.getDescription().equals(existingCourse.getDescription())) {
            existingCourse.setDescription(course.getDescription());
        }
        courseDAO.update(existingCourse);
        LOG.info("Course successfully modified. CourseId: ", existingCourse.getId());
    }

    @Override
    public void checkPrivilege(String email, String courseName) throws Exception{
        User user = userBean.getUserByEmail(email);
        Optional<Course> course = courseDAO.find(courseName);
        if(!course.isPresent() || !course.get().getCreator().equals(user)) {
            throw new SecurityException("The given user have no rights to modify the given course");
        }
    }
}
