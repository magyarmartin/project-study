package hu.study.rest.user;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import hu.study.model.dto.UserWIthPasswordDto;
import hu.study.model.mapper.UserWithPasswordMapper;
import hu.study.rest.response.ServerResponse;
import hu.study.rest.response.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.study.ejb.UserBeanIF;
import hu.study.model.entity.User;
import hu.study.rest.interfaces.Secured;
import hu.study.rest.roles.Role;

/**
 * Created by martin4955 on 2017. 08. 13..
 */

@Path( "/api/user" )
@RequestScoped
public class UserHandler {

    private static final Logger LOG = LogManager.getLogger(UserHandler.class);

    @Inject
    UserBeanIF userBean;

    @GET
    @Secured( { Role.STUDENT, Role.INSTRUCTOR } )
    @Produces( MediaType.APPLICATION_JSON )
    public User getUser( @QueryParam( "email" ) final String email ) {
        try {
            User user = userBean.getUserByEmail(email);
            user.setPassword(null);
            return user;
        } catch (Exception e) {
            LOG.error("getUser exception, maybe there is no user with email: " + email, e);
            return null;
        }
    }

    @POST
    @Secured( { Role.STUDENT, Role.INSTRUCTOR } )
    @Consumes( MediaType.APPLICATION_JSON )
    @Produces( MediaType.APPLICATION_JSON )
    public Response modifyUser( final UserWIthPasswordDto userDto, @Context final SecurityContext securityContext ) {
        try {
            User user = UserWithPasswordMapper.INSTANCE.userWithPasswordDtoToUser(userDto);
            String principalEmail = securityContext.getUserPrincipal().getName();
            userBean.modifyUser(user, principalEmail);
            return Response.ok(new ServerResponse( Status.OK)).build();
        } catch (Exception e) {
            LOG.error("modifyUser error", e);
            return Response.ok(new ServerResponse(Status.ERROR)).build();
        }
    }

    @DELETE
    @Secured( { Role.STUDENT, Role.INSTRUCTOR } )
    public Response deleteUser( @Context final SecurityContext securityContext ) {
        try {
            String principalEmail = securityContext.getUserPrincipal().getName();
            userBean.deleteUser(principalEmail);
            return Response.ok().build();
        } catch (Exception e) {
            LOG.error("deleteUser", e);
            return Response.notModified().build();
        }
    }

}
