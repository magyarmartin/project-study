package hu.study.rest.course;

import hu.study.ejb.CourseBeanIF;
import hu.study.ejb.UserBeanIF;
import hu.study.model.entity.Course;
import hu.study.rest.interfaces.Secured;
import hu.study.rest.roles.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/api/course")
@RequestScoped
public class CourseHandler {

    private static final Logger LOG = LogManager.getLogger( CourseHandler.class );

    @Inject
    CourseBeanIF courseBean;

    @Inject
    UserBeanIF userBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Course getCourse(@QueryParam("name") String courseName) {
        try {
            return courseBean.getCourse(courseName);
        } catch (Exception e) {
            LOG.error("getCourse exception. Maybe the course is not exists with the given name: " +courseName, e);
        }
        return null;
    }

    @GET
    @Path("{limit}/{ordering}/{order}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> getCourses(@PathParam("limit") int limit, @PathParam("ordering") String ordering,
                                   @PathParam("order") String order, @QueryParam("courseName") String name) {
        return courseBean.getCourses(name, limit, ordering, order);
    }

    @POST
    @Secured({Role.INSTRUCTOR})
    public Response createCourse(Course course) {
        try {
            courseBean.createCourse(course);
            return Response.ok().build();
        } catch (Exception e) {
            LOG.error("createCourse", e);
            return Response.notModified().build();
        }
    }

    @POST
    @Secured({Role.STUDENT})
    @Path("/signUp")
    public Response signUp(@FormParam("courseName") String courseName, @Context SecurityContext securityContext) {
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
    @Path("/modify")
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Role.INSTRUCTOR})
    public Response modifyCourse(@FormParam("courseName") String courseName, Course course, @Context SecurityContext securityContext) {
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
    @Secured({Role.INSTRUCTOR})
    public Response deleteCourse(@FormParam("courseName") String name, @Context SecurityContext securityContext) {
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
