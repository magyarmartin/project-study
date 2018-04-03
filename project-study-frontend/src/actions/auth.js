import axios from 'axios';
import { AUTHENTICATION } from  '../types/EventTypes';

export default function(credidentals) {
  const request = axios.post('http://localhost:8080/projectstudy/api/auth',
    {
      email: credidentals.email, 
      password: credidentals.password
    })

	return {
		type: AUTHENTICATION,
		payload: request
	}
}