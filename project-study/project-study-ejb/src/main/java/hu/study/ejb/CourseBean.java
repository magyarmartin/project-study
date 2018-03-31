package hu.study.ejb;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.study.model.dao.CourseDAO;
import hu.study.model.entity.Course;
import hu.study.model.entity.User;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

/**
 * Created by martin4955 on 2017. 09. 09..
 */
@Log4j2
@NoArgsConstructor
@Stateless( name = "CourseEJB" )
public class CourseBean implements CourseBeanIF {

    @PersistenceContext( name = "ProStu" )
    protected EntityManager entityManager;

    @EJB
    protected UserBeanIF userBean;
    protected CourseDAO courseDAO;

    @PostConstruct
    public void setUp() {
        courseDAO = new CourseDAO(entityManager);
    }

    @Override
    public Course getCourse( @NonNull final String name ) {
        Optional<Course> course = courseDAO.find(name);
        if ( course.isPresent() ) {
            return course.get();
        }
        return null;
    }

    @Override
    public List<Course> getCourses( @NonNull final String courseName, final int limit, final String ordering, final String order ) {
        return courseDAO.findLikeName(courseName, ordering, order, limit);
    }

    @Override
    public void createCourse( @NonNull final Course course ) {

    }

    @Override
    public void deleteCourse( @NonNull final String courseName ) {
        Course existingCourse = getCourse(courseName);
        courseDAO.delete(existingCourse);
        log.info("Successfully deleted the course: ", existingCourse);
    }

    @Override
    public void modifyCourse( @NonNull final String courseName, @NonNull final Course course ) {
        Course existingCourse = getCourse(courseName);
        if ( !course.getName().equals(existingCourse.getName()) ) {
            existingCourse.setName(course.getName());
        }
        if ( !course.getDescription().equals(existingCourse.getDescription()) ) {
            existingCourse.setDescription(course.getDescription());
        }
        courseDAO.update(existingCourse);
        log.info("Course successfully modified. CourseId: ", existingCourse.getId());
    }

    @Override
    public void checkPrivilege( @NonNull final String email, @NonNull final String courseName ) throws Exception {
        User user = userBean.getUserByEmail(email);
        Optional<Course> course = courseDAO.find(courseName);
        if ( !course.isPresent() || !course.get().getCreator().equals(user) ) {
            throw new SecurityException("The given user have no rights to modify the given course");
        }
    }
}
