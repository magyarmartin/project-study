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

import hu.study.ejb.UserBeanIF;
import hu.study.model.dto.UserDto;
import hu.study.model.entity.User;
import hu.study.model.mapper.UserMapper;
import hu.study.rest.response.ServerResponse;
import hu.study.rest.response.Status;

@Path( "/api/registration" )
@RequestScoped
public class Registration {

    private static final Logger LOG = LogManager.getLogger(Registration.class);

    @Inject
    UserBeanIF userBean;

    @POST
    @Produces( MediaType.APPLICATION_JSON )
    @Consumes( MediaType.APPLICATION_JSON )
    public Response registration( final UserDto userDto ) {
        try {
            User user = UserMapper.INSTANCE.userDtoToUser(userDto);
            if ( isValidPassword(user.getPassword()) ) {
                if ( !userBean.isExistingUser(user) ) {
                    userBean.registerUser(user);
                    return Response.ok(new ServerResponse(Status.OK)).build();
                } else {
                    return Response.ok(new ServerResponse(Status.EXISTS)).build();
                }
            } else {
                return Response.ok(new ServerResponse(Status.INVALID)).build();
            }
        } catch (Exception e) {
            LOG.error("Registration error", e);
            return Response.ok(new ServerResponse(Status.ERROR)).build();
        }
    }

    private boolean isValidPassword( final String pwd ) {
        boolean isAtLeast6 = pwd.length() >= 8;
        boolean hasNumber = pwd.matches(".*\\d+.*");;
        boolean hasUppercase = !pwd.equals(pwd.toLowerCase());
        boolean hasLowercase = !pwd.equals(pwd.toUpperCase());
        if ( isAtLeast6 && hasNumber && hasLowercase && hasUppercase ) {
            return true;
        } else {
            return false;
        }
    }

}
