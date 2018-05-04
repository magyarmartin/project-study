import { FIRSTNAME_CHANGED, LASTNAME_CHANGED, EMAIL_CHANGED, PASSWORD_CHANGED, PASSWORD_CONF_CHANGED, IS_TEACHER_CHANGED, DESCRIPTION_CHANGED, SUBMIT,
    REGISTRATION_START } from  '../types/EventTypes';

const userDetail = {
    email: '',
    password: '',
    passwordConf: '',
    firstName: '',
    lastName: '',
    type: 'student',
    description: '',
    emailValid: true,
    passwordValid: true,
    passwordConfValid: true,
    firstNameValid: true,
    lastNameValid: true,
    emailError: '',
    passwordError: 'The password should be at least 6 character!',
    passwordConfError: 'The password confirmation should be equal the password!',
    firstNameError: 'The first name should be not empty!',
    lastNameError: 'The last name should be not empty!',
    submited: false
}

export default function(state = userDetail, event) {
    switch (event.type) {
        case FIRSTNAME_CHANGED:
            return Object.assign({}, state, event.payload, {submited: false});
        case LASTNAME_CHANGED:
            return Object.assign({}, state, event.payload, {submited: false});
        case EMAIL_CHANGED:
            return Object.assign({}, state, event.payload, {submited: false});
        case PASSWORD_CHANGED:
            return Object.assign({}, state, event.payload, {submited: false});
        case PASSWORD_CONF_CHANGED:
            return Object.assign({}, state, event.payload, {submited: false});
        case IS_TEACHER_CHANGED:
            return Object.assign({}, state, event.payload, {submited: false});
        case DESCRIPTION_CHANGED:
            return Object.assign({}, state, event.payload, {submited: false});
        case SUBMIT:
            return Object.assign({}, state, {submited: true})
        case REGISTRATION_START:
            return Object.assign({}, state, {submited: false})
        default:
            return state;
    }
}