import { LOGIN_SUCCESS } from  '../types/EventTypes';

export default function(token) {
    let payload = 'OK';
    try {
        window.localStorage.setItem('pro-stu-token', token);
        console.log('stored')
    } catch(err) {
        payload = 'ERROR';
    }
  
    return {
        type: LOGIN_SUCCESS,
        payload: payload
    }
  }