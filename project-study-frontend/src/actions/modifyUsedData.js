import { MODIFY_USER_START, MODIFY_USER_END } from  '../types/EventTypes';
import fetch from 'cross-fetch';

function startModification() {
    return {
      type: MODIFY_USER_START
    }
}

function finishModification(json) {
    return {
      type: MODIFY_USER_END,
      payload: json
    }
}

export default function(user) {

    return function (dispatch) {
      dispatch(startModification());
      
      fetch('http://localhost:8080/projectstudy/api/user', {
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
        json => dispatch(finishModification(json))
      )
    }
    
  }