import { FIRSTNAME_CHANGED, LASTNAME_CHANGED, EMAIL_CHANGED, PASSWORD_CHANGED, PASSWORD_CONF_CHANGED, IS_TEACHER_CHANGED, DESCRIPTION_CHANGED, SUBMIT } from  '../types/EventTypes';
import Validation from './../utils/Validation.js';

export default function(details) {
    switch(details.type) {
        case FIRSTNAME_CHANGED:
            let firstNameValid = validateFirstName(details.value);
            return {
                type: FIRSTNAME_CHANGED,
                payload: {
                    firstName: details.value,
                    firstNameValid: firstNameValid
                }
            }
        case LASTNAME_CHANGED:
            let lastNameValid = validateLastName(details.value);
            return {
                type: LASTNAME_CHANGED,
                payload: {
                    lastName: details.value,
                    lastNameValid: lastNameValid
                }
            }
        case EMAIL_CHANGED:
            let emailValid = validateEmail(details.value);
            return {
                type: EMAIL_CHANGED,
                payload: emailValid
            }
        case PASSWORD_CHANGED:
            let passwordValid = validatePassworld(details.value);
            return {
                type: PASSWORD_CHANGED,
                payload: {
                    passwordValid: passwordValid,
                    password: details.value
                }
            }
        case PASSWORD_CONF_CHANGED:
            let passwordConfValid = validatePassworld(details.value.passwordConf);
            return {
                type: PASSWORD_CONF_CHANGED,
                payload: {
                    passwordConfValid: passwordConfValid,
                    passwordConf: details.value.passwordConf
                }
            }
        case IS_TEACHER_CHANGED:
            return {
                type: IS_TEACHER_CHANGED,
                payload: {
                    type: details.value
                }
            }
        case DESCRIPTION_CHANGED:
            return {
                type: DESCRIPTION_CHANGED,
                payload: {
                    description: details.value
                }
            }
        case SUBMIT:
            return {type: SUBMIT}
    }
}

function validateFirstName( firstName ) {
    return !Validation.isEmpty( firstName );
}

function validateLastName( lastName ) {
    return !Validation.isEmpty( lastName );
}

function validateEmail( email ) {
    if ( Validation.isEmpty(email) ) {
        return {email: email, emailValid: false, emailError: 'The email should be not empty'};
    } else if ( !Validation.isValidEmail(email) ) {
        return {email: email, emailValid: false, emailError: 'This is not a valid email'};
    } else {
        return {email: email, emailValid: true, emailError: ''};
  }
}

function validatePassworld( password ) {
    return !Validation.isShorterThan( password, 6 );
}

function validatePassworldConf( password, passwordConf ) {
    return Validation.isTheSame( passwordConf, password );
}