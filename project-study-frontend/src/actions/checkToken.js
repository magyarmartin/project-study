import { TOKEN_CHECK, USER_UPDATE } from  '../types/EventTypes';
import fetch from 'cross-fetch';

function checkedToken(fetching, checked) {
	return {
		type: TOKEN_CHECK,
		fetching: fetching,
		checked: checked
	}
}

function updateUserData(json, token) {
	return {
		type: USER_UPDATE,
		payload: json.payload,
		token: token
	}
}

export default function() {

	return function (dispatch) {
		let token = window.localStorage.getItem('pro-stu-token');
		dispatch(checkedToken(true, false));

		if ( token !== null && token !== '' ) {
			return fetch('http://localhost:8080/projectstudy/api/auth', {
				cache: 'no-cache',
				credentials: 'same-origin',
				headers: {
					'content-type': 'application/json',
					'Authorization': `Bearer ${token}`
				},
				method: 'GET',
				mode: 'cors',
				redirect: 'follow',
				referrer: 'no-referrer',
			}).then(
				response => response.json(),
				error => console.log('An error occurred.', error)
			).then(
				json => {
					dispatch(updateUserData(json, token));
					dispatch(checkedToken(false, true));
				}
			)
		} else {
			dispatch(checkedToken(false, true));
		}
	}

}