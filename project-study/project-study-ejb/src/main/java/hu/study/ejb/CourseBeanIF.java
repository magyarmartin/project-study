package hu.study.ejb;

import java.util.List;

import hu.study.model.entity.Course;

/**
 * Created by martin4955 on 2017. 09. 09..
 */
public interface CourseBeanIF {

    Course getCourse( String name );

    List<Course> getCourses( String name, int limit, String ordering, String order );

    void createCourse( Course course );

    void deleteCourse( String courseName );

    void modifyCourse( String courseName, Course course );

    void checkPrivilege( String email, String courseName ) throws Exception;

}
