import { AUTHENTICATION_START, AUTHENTICATION_SUCCESS, AUTHENTICATION_INVALID, AUTHENTICATION_ERROR, USER_UPDATE, LOGOUT } from  '../types/EventTypes';

const defaultSate = {
		authenticated: undefined,
		token: '',
		firstName: '',
		lastName: '',
		email: '',
		instructor: undefined,
		description: '',
		error: false
	}

export default function(state = defaultSate, event) {
	switch (event.type) {
		case AUTHENTICATION_START:
			return Object.assign({}, state, {isFetching: true});
			break;
		case AUTHENTICATION_SUCCESS:
			return Object.assign({}, state, event.payload, {authenticated: true, isFetching: false});
			break;
		case AUTHENTICATION_INVALID:
			return Object.assign({}, state, {authenticated: false, error: false, isFetching: false});
			break;
		case AUTHENTICATION_ERROR:
			return Object.assign({}, state, {authenticated: false, error: true, isFetching: false});
			break;
		case USER_UPDATE:
			return Object.assign({}, state, event.payload, {authenticated: true, token: event.token})
			break;
		case LOGOUT:
			return defaultSate;
			break;
		default: 
			return state;
	}
}