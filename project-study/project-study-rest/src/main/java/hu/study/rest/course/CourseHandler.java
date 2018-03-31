package hu.study.rest.course;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.study.ejb.CourseBeanIF;
import hu.study.ejb.UserBeanIF;
import hu.study.model.entity.Course;
import hu.study.rest.interfaces.Secured;
import hu.study.rest.roles.Role;

@Path( "/api/course" )
@RequestScoped
public class CourseHandler {

    private static final Logger LOG = LogManager.getLogger(CourseHandler.class);

    @Inject
    CourseBeanIF courseBean;

    @Inject
    UserBeanIF userBean;

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    public Course getCourse( @QueryParam( "name" ) final String courseName ) {
        try {
            return courseBean.getCourse(courseName);
        } catch (Exception e) {
            LOG.error("getCourse exception. Maybe the course is not exists with the given name: " + courseName, e);
        }
        return null;
    }

    @GET
    @Path( "{limit}/{ordering}/{order}" )
    @Produces( MediaType.APPLICATION_JSON )
    public List<Course> getCourses( @PathParam( "limit" ) final int limit, @PathParam( "ordering" ) final String ordering,
        @PathParam( "order" ) final String order, @QueryParam( "courseName" ) final String name ) {
        return courseBean.getCourses(name, limit, ordering, order);
    }

    @POST
    @Secured( { Role.INSTRUCTOR } )
    public Response createCourse( final Course course ) {
        try {
            courseBean.createCourse(course);
            return Response.ok().build();
        } catch (Exception e) {
            LOG.error("createCourse", e);
            return Response.notModified().build();
        }
    }

    @POST
    @Secured( { Role.STUDENT } )
    @Path( "/signUp" )
    public Response signUp( @FormParam( "courseName" ) final String courseName, @Context final SecurityContext securityContext ) {
        try {
            Course course = courseBean.getCourse(courseName);
            userBean.signUpCourse(securityContext.getUserPrincipal().getName(), course);
            return Response.ok().build();
        } catch (Exception e) {
            LOG.error("signUp", e);
            return Response.notModified().build();
        }
    }

    @POST
    @Path( "/modify" )
    @Consumes( MediaType.APPLICATION_JSON )
    @Secured( { Role.INSTRUCTOR } )
    public Response modifyCourse( @FormParam( "courseName" ) final String courseName, final Course course,
        @Context final SecurityContext securityContext ) {
        try {
            courseBean.checkPrivilege(securityContext.getUserPrincipal().getName(), courseName);
            courseBean.modifyCourse(courseName, course);
            return Response.ok().build();
        } catch (Exception e) {
            LOG.error("modifyCourse", e);
            return Response.notModified().build();
        }
    }

    @DELETE
    @Secured( { Role.INSTRUCTOR } )
    public Response deleteCourse( @FormParam( "courseName" ) final String name, @Context final SecurityContext securityContext ) {
        try {
            courseBean.checkPrivilege(securityContext.getUserPrincipal().getName(), name);
            courseBean.deleteCourse(name);
            return Response.ok().build();
        } catch (Exception e) {
            LOG.error("deleteCourse", e);
            return Response.notModified().build();
        }
    }

}
