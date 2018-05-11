import { AUTHENTICATION_START, AUTHENTICATION_SUCCESS, AUTHENTICATION_INVALID, AUTHENTICATION_ERROR, USER_UPDATE, LOGOUT,
	MODIFY_USER_START, MODIFY_USER_SUCCESS, MODIFY_USER_ERROR, CHANGED_FLAG, DELETE_USER_START, DELETE_USER_SUCCESS, 
	DELETE_USER_ERROR } from  '../types/EventTypes';

const defaultSate = {
		authenticated: undefined,
		token: '',
		firstName: '',
		lastName: '',
		email: '',
		instructor: undefined,
		description: '',
		error: false,
		changed: false
	}

export default function(state = defaultSate, event) {
	switch (event.type) {
		case AUTHENTICATION_START || MODIFY_USER_START || DELETE_USER_START:
			console.log('start')
			return Object.assign({}, state, {isFetching: true});
			break;
		case AUTHENTICATION_SUCCESS:
			return Object.assign({}, state, event.payload, {authenticated: true, isFetching: false});
			break;
		case AUTHENTICATION_INVALID:
			return Object.assign({}, state, {authenticated: false, error: false, isFetching: false});
			break;
		case AUTHENTICATION_ERROR || MODIFY_USER_ERROR || DELETE_USER_ERROR:
			return Object.assign({}, state, {authenticated: false, error: true, isFetching: false});
			break;
		case USER_UPDATE:
			return Object.assign({}, state, event.payload, {authenticated: true, token: event.token})
			break;
		case LOGOUT || DELETE_USER_SUCCESS:
			return defaultSate;
			break;
		case MODIFY_USER_SUCCESS:
			return Object.assign({}, state, event.payload, {changed: true, authenticated: true, isFetching: false});
		case CHANGED_FLAG:
			return Object.assign({}, state, {changed: event.payload});
		default: 
			return state;
	}
}