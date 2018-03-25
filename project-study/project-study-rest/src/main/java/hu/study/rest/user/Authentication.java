package hu.study.rest.user;

import hu.study.ejb.TokenBeanIF;
import hu.study.ejb.UserBeanIF;
import hu.study.model.entity.Token;
import hu.study.model.entity.User;
import hu.study.rest.interfaces.Secured;
import hu.study.rest.roles.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/auth")
@RequestScoped
public class Authentication {

    private static final Logger LOG = LogManager.getLogger( Authentication.class );

    @Inject
    UserBeanIF userBean;

    @Inject
    TokenBeanIF tokenBean;

    @Secured
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
    @Consumes(MediaType.APPLICATION_JSON)
    public Response isValidUser(User userObject) {

        String email = userObject.getEmail();
        String password = userObject.getPassword();
        try {
            User user = userBean.getUserIfValid(email, password);
            userBean.deleteTokenIfExists(user);
            Token token = userBean.issueToken(user);
            return Response.ok(token.getToken()).build();
        } catch (IllegalStateException e) {
            LOG.info("Wrong user credidentals", e);
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (Exception e) {
            LOG.error("Error isValidUser: ", e);
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}
