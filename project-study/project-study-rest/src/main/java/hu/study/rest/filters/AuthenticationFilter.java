package hu.study.rest.filters;

import java.io.IOException;
import java.security.Principal;

import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.study.ejb.TokenBeanIF;
import hu.study.model.entity.Token;
import hu.study.rest.interfaces.Secured;

/**
 * Created by martin4955 on 2017. 08. 12..
 */
@Secured
@Provider
@Priority( Priorities.AUTHENTICATION )
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final Logger LOG = LogManager.getLogger(AuthenticationFilter.class);

    @EJB
    TokenBeanIF tokenBean;

    @Override
    public void filter( final ContainerRequestContext requestContext ) throws IOException {

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if ( authorizationHeader == null || !authorizationHeader.startsWith("Bearer ") ) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            Token validToken = tokenBean.getToken(token);
            setUser(validToken.getUser().getEmail(), requestContext);
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    public void setUser( final String email, final ContainerRequestContext requestContext ) {
        final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
        requestContext.setSecurityContext(new SecurityContext() {

            @Override
            public Principal getUserPrincipal() {

                return new Principal() {

                    @Override
                    public String getName() {
                        return email;
                    }
                };
            }

            @Override
            public boolean isUserInRole( final String role ) {
                return true;
            }

            @Override
            public boolean isSecure() {
                return currentSecurityContext.isSecure();
            }

            @Override
            public String getAuthenticationScheme() {
                return "Bearer";
            }
        });
    }
}
