package hu.study.rest;

import com.sun.net.httpserver.Authenticator;
import hu.DemoBean;
import hu.study.ejb.UserBeanIF;
import hu.study.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/auth")
@RequestScoped
public class Authentication {

    private static final Logger LOG = LogManager.getLogger( Authentication.class );

    @Inject
    UserBeanIF userBean;

    @EJB(name = "DemoEJB")
    DemoBean asd;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User sayPlainTextHello() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("asd");
        user.setEmail("a@b.c");
        return user;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response isValidUser(User user) {

        try {
            userBean.validatingUser(user);
            return Response.ok().build();
        } catch (IllegalStateException e) {
            LOG.info("Wrong user credidentals", e);
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (Exception e) {
            LOG.error("Error isValidUser: ", e);
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}
