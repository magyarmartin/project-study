import { REGISTRATION_START, REGISTRATION_SUCCESS,REGISTRATION_INVALID, REGISTRATION_ERROR, REGISTRATION_EXISTS } from  '../types/EventTypes';

const defaultState = {
    isFetching: false,
    completed: false,
    error: false,
    invalid: false,
    exists: false,
    status: ''
}


export default function(state = defaultState, event) {
    console.log(event.payload)
    switch (event.type) {
        case REGISTRATION_START :
            return Object.assign({}, state, {isFetching: true});
            break;
        case REGISTRATION_SUCCESS :
            return Object.assign({}, state, {isFetching: false, completed: true, error: false, invalid: false});
            break;
        case REGISTRATION_INVALID :
            return Object.assign({}, state, {isFetching: false, error: false, invalid: true, exists: false});
            break;
        case REGISTRATION_ERROR :
            return Object.assign({}, state, {isFetching: false, error: true, invalid: false, exists: false});
            break;
        case REGISTRATION_EXISTS :
            return Object.assign({}, state, {isFetching: false, error: false, invalid: false, exists: true});
            break;
        default :
            state;
    }
    return state;
}