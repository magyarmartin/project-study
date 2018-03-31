package hu.study.model.exception;

/**
 * This is an exception for authentication. Throw this if the authentication was not successfull bz the user's fault.
 *
 * @author magyarm
 *
 */
public class AuthenticationException extends Exception {

    public AuthenticationException( final String msg ) {
        super(msg);
    }

    private static final long serialVersionUID = 1L;

}
