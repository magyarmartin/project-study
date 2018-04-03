import { REGISTRATION } from  '../types/EventTypes';

export default function(user) {
    console.log(user);
    const request = fetch('http://localhost:8080/projectstudy/api/registration', {
      body: JSON.stringify(user), // must match 'Content-Type' header
      cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
      credentials: 'same-origin', // include, *omit
      headers: {
        'user-agent': 'Mozilla/4.0 MDN Example',
        'content-type': 'application/json'
      },
      method: 'POST', // *GET, PUT, DELETE, etc.
      mode: 'cors', // no-cors, *same-origin
      redirect: 'follow', // *manual, error
      referrer: 'no-referrer', // *client
    });

	return {
		type: REGISTRATION,
		payload: request
	}
}