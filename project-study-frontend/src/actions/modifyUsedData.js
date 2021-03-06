import { MODIFY_USER_START, MODIFY_USER_SUCCESS, MODIFY_USER_ERROR, CHANGED_FLAG } from  '../types/EventTypes';
import fetch from 'cross-fetch';

function startModification() {
  return {
    type: MODIFY_USER_START
  }
}

function finishModification(json) {
  if(json.status === 'OK') {
    return {
      type: MODIFY_USER_SUCCESS,
      payload: json
    }
  } else {
    return {
      type: MODIFY_USER_ERROR,
      payload: json
    }
  }
}

export function modifyUserData(user, token) {
  return function (dispatch) {
    dispatch(startModification());
      
    fetch('http://localhost:8080/projectstudy/api/user', {
      body: JSON.stringify(user),
      cache: 'no-cache',
      credentials: 'same-origin',
      headers: {
        'content-type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      method: 'POST',
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

export function setModificationFlag(flag) {
  return {
    type: CHANGED_FLAG,
    payload: flag
  }
}