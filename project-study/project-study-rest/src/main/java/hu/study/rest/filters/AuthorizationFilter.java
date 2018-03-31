package hu.study.rest.filters;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import hu.study.ejb.UserBeanIF;
import hu.study.model.entity.User;
import hu.study.rest.interfaces.Secured;
import hu.study.rest.roles.Role;

/**
 * Created by martin4955 on 2017. 08. 13..
 */
public class AuthorizationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @EJB
    UserBeanIF userBean;

    @Override
    public void filter( final ContainerRequestContext requestContext ) throws IOException {
        Class<?> resourceClass = resourceInfo.getResourceClass();
        List<Role> classRoles = extractRoles(resourceClass);

        Method resourceMethod = resourceInfo.getResourceMethod();
        List<Role> methodRoles = extractRoles(resourceMethod);

        String email = requestContext.getSecurityContext().getUserPrincipal().getName();

        try {
            if ( methodRoles.isEmpty() ) {
                checkPermissions(classRoles, email);
            } else {
                checkPermissions(methodRoles, email);
            }

        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
        }
    }

    private List<Role> extractRoles( final AnnotatedElement annotatedElement ) {
        if ( annotatedElement == null ) {
            return new ArrayList<Role>();
        } else {
            Secured secured = annotatedElement.getAnnotation(Secured.class);
            if ( secured == null ) {
                return new ArrayList<Role>();
            } else {
                Role[] allowedRoles = secured.value();
                return Arrays.asList(allowedRoles);
            }
        }
    }

    private void checkPermissions( final List<Role> allowedRoles, final String email ) throws Exception {
        User user = userBean.getUserByEmail(email);
        if ( user.isInstructor() && allowedRoles.contains(Role.INSTRUCTOR) ) {
            return;
        } else if ( !user.isInstructor() && allowedRoles.contains(Role.STUDENT) ) {
            return;
        } else {
            throw new NotAuthorizedException("Not authorized");
        }
    }
}
