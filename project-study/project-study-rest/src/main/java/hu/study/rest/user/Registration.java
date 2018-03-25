package hu.study.rest.user;

import hu.study.ejb.UserBeanIF;
import hu.study.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/registration")
@RequestScoped
public class Registration {

    private static final Logger LOG = LogManager.getLogger( Registration.class );

    @Inject
    UserBeanIF userBean;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String registration(User user) {
        try {
            if(isValidPassword(user.getPassword())) {
                if(!userBean.isExistingUser(user)) {
                    userBean.registerUser(user);
                    return "{\"response\": \"ok\"}";
                } else {
                    return "{\"response\": \"existing user\"}";
                }
            } else {
                return "{\"response\": \"invalid password\"}";
            }
        } catch (Exception e) {
            LOG.error("Registration error", e);
            return "{\"response\": \"error\"}";
        }
    }

    private boolean isValidPassword(String pwd) {
        boolean isAtLeast6   = pwd.length() >= 8;
        boolean hasNumber   = pwd.matches(".*\\d+.*");;
        boolean hasUppercase = !pwd.equals(pwd.toLowerCase());
        boolean hasLowercase = !pwd.equals(pwd.toUpperCase());
        if(isAtLeast6 && hasNumber && hasLowercase && hasUppercase) {
            return true;
        } else {
            return false;
        }
    }

}
