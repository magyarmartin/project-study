import { TOKEN_CHECK } from  '../types/EventTypes';

export default function(credidentals) {
    let token = window.localStorage.getItem('pro-stu-token');

	return {
		type: TOKEN_CHECK,
		payload: true
	}
}