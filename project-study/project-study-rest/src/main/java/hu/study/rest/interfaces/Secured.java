package hu.study.rest.interfaces;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.ws.rs.NameBinding;

import hu.study.rest.roles.Role;

@NameBinding
@Retention( RUNTIME )
@Target( { TYPE, METHOD } )
public @interface Secured {
    Role[] value() default {};
}
