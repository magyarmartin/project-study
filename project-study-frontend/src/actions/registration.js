import { REGISTRATION } from  '../types/EventTypes';
import fetch from 'cross-fetch';
import { REGISTRATION_START, REGISTRATION_SUCCESS,REGISTRATION_INVALID, REGISTRATION_ERROR, REGISTRATION_EXISTS } from  '../types/EventTypes';

function startAuthenticate() {
  return {
    type: REGISTRATION_START
  }
}

function finishRegistration(json) {
  let type;
  if(json.status === 'OK') {
    type = REGISTRATION_SUCCESS;
  } else if(json.status === 'EXISTS') {
    type = REGISTRATION_EXISTS;
  } else if(json.status === 'INVALID') {
    type = REGISTRATION_INVALID;
  } else if(json.status === 'ERROR') {
    type = REGISTRATION_ERROR;
  }
  return {
    type: type,
    payload: json
  }
}

export default function(user) {

  return function (dispatch) {
    dispatch(startAuthenticate());
    
    fetch('http://localhost:8080/projectstudy/api/registration', {
      body: JSON.stringify(user),
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
      json => dispatch(finishRegistration(json))
    )
  }
  
}