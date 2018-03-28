
const defaultSate = {
		authenticated: false,
		token: '',
		firstName: '',
		lastName: '',
		email: '',
		instructor: undefined
	}

export default function(state = defaultSate, event) {
	console.log(event);
	return state;
}