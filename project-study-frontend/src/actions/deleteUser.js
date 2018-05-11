import { DELETE_USER_START, DELETE_USER_SUCCESS, DELETE_USER_ERROR } from  '../types/EventTypes';
import fetch from 'cross-fetch';

function startModification() {
  return {
    type: DELETE_USER_START
  }
}

function finishModification(json) {
  if(json.status === 'OK') {
    return {
      type: DELETE_USER_SUCCESS,
      payload: json
    }
  } else {
    return {
      type: DELETE_USER_ERROR,
      payload: json
    }
  }
}

export function deleteUser(token) {
  return function (dispatch) {
    dispatch(startModification());
      
    fetch('http://localhost:8080/projectstudy/api/user', {
      cache: 'no-cache',
      credentials: 'same-origin',
      headers: {
        'content-type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      method: 'DELETE',
      mode: 'cors',
      redirect: 'follow',
      referrer: 'no-referrer',
    }).then(
      response => response.json(),
      error => console.log('An error occurred.', error)
    ).then(
      json => dispatch(finishModification(json))
    )
  }   
}