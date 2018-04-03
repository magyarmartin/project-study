import { AUTHENTICATION } from  '../types/EventTypes';

const defaultSate = {
		authenticated: undefined,
		token: '',
		firstName: '',
		lastName: '',
		email: '',
		instructor: undefined,
		error: false
	}

export default function(state = defaultSate, event) {
	let data = event.payload ? event.payload.data : '';
	switch (event.type) {
		case AUTHENTICATION:
			if(data.status === 'OK') {
				data.payload.authenticated = true;
				data.payload.firstTime = true
				return data.payload;
			} else if(data.status === 'INVALID') {
				return {
					authenticated: false,
					token: '',
					firstName: '',
					lastName: '',
					email: '',
					instructor: undefined,
					error: false
				};
			} else {
				return {
					authenticated: false,
					token: '',
					firstName: '',
					lastName: '',
					email: '',
					instructor: undefined,
					error: true
				}
			}
			return defaultSate;
			break;
		default: 
			return state;
	}
}