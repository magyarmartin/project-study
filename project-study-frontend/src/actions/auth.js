import fetch from 'cross-fetch';
import { AUTHENTICATION_START, AUTHENTICATION_SUCCESS, AUTHENTICATION_INVALID, AUTHENTICATION_ERROR } from  '../types/EventTypes';

function startAuthenticate() {
  return {
    type: AUTHENTICATION_START
  }
}

function finishAuthentication(json) {
  let type;
  switch (json.status) {
    case 'INVALID':
      type = AUTHENTICATION_INVALID
      break;
    case 'ERROR':
      type = AUTHENTICATION_ERROR
      break;
    default:
      type = AUTHENTICATION_SUCCESS
  }
  return {
		type: type,
		payload: json.payload
	}
}

export default function(credidentals) {

  return function (dispatch) {
    dispatch(startAuthenticate());

    return fetch('http://localhost:8080/projectstudy/api/auth', {
      body: JSON.stringify({
        email: credidentals.email, 
        password: credidentals.password
      }),
      cache: 'no-cache',
      credentials: 'same-origin',
      headers: {
        'content-type': 'application/json'
      },
      method: 'POST',
      mode: 'cors',
      redirect: 'follow',
      referrer: 'no-referrer',
    }).then(
      response => response.json(),
      error => console.log('An error occurred.', error)
    ).then(
      json => dispatch(finishAuthentication(json))
    )
  }

}