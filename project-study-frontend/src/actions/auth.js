export default function(credidentals) {
	const request = fetch('http://localhost:8080/projectstudy/api/auth', {
      body: JSON.stringify({email: credidentals.email, password: credidentals.password}), // must match 'Content-Type' header
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
		type: 'AUTHENTICATION',
		payload: request
	}
}