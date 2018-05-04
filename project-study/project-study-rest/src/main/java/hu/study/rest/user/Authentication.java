package hu.study.rest.user;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import hu.study.model.mapper.UserMapper;
import hu.study.rest.interfaces.Secured;
import hu.study.rest.roles.Role;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.study.ejb.TokenBeanIF;
import hu.study.ejb.UserBeanIF;
import hu.study.model.dto.BaseDto;
import hu.study.model.entity.Token;
import hu.study.model.entity.User;
import hu.study.model.exception.AuthenticationException;
import hu.study.model.mapper.AuthenticationMapper;
import hu.study.rest.response.ServerResponse;
import hu.study.rest.response.Status;

@Path( "/api/auth" )
@RequestScoped
@Log4j2
public class Authentication {

    @Inject
    UserBeanIF userBean;

    @Inject
    TokenBeanIF tokenBean;

    @POST
    @Produces( MediaType.APPLICATION_JSON )
    @Consumes( MediaType.APPLICATION_JSON )
    public Response isValidUser( final User userObject ) {
        try {
            User user = userBean.getUserIfValid(userObject.getEmail(), userObject.getPassword());
            userBean.deleteTokenIfExists(user);
            Token token = userBean.issueToken(user);
            BaseDto auth = AuthenticationMapper.INSTANCE.userAndTokenToAuthenticationDto(user, token);
            return Response.ok(new ServerResponse(Status.OK, auth)).build();
        } catch (AuthenticationException e) {
            log.info("Wrong user credidentals", e);
            return Response.ok(new ServerResponse(Status.INVALID)).build();
        } catch (Exception e) {
            log.error("Error isValidUser: ", e);
            return Response.ok(new ServerResponse(Status.ERROR)).build();
        }
    }

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    @Consumes( MediaType.APPLICATION_JSON )
    @Secured( { Role.STUDENT, Role.INSTRUCTOR } )
    public Response getUserByToken( final String token, @Context final SecurityContext securityContext ) {
        try {
            String principalEmail = securityContext.getUserPrincipal().getName();
            User user = userBean.getUserByEmail( principalEmail );
            BaseDto userDto = UserMapper.INSTANCE.userToUserDto( user );
            return Response.ok(new ServerResponse( Status.OK, userDto )).build();
        } catch( Exception e ) {
            log.error( "Error getUserByToken", e );
            return Response.ok(new ServerResponse( Status.ERROR )).build();
        }
    }

}
