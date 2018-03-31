package hu.study.rest.user;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.study.ejb.TokenBeanIF;
import hu.study.ejb.UserBeanIF;
import hu.study.model.entity.Token;
import hu.study.model.entity.User;

@Path( "/api/auth" )
@RequestScoped
public class Authentication {

    private static final Logger LOG = LogManager.getLogger(Authentication.class);

    @Inject
    UserBeanIF userBean;

    @Inject
    TokenBeanIF tokenBean;

    @POST
    @Produces( MediaType.APPLICATION_JSON )
    @Consumes( MediaType.APPLICATION_JSON )
    public Response isValidUser( final User userObject ) {

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
