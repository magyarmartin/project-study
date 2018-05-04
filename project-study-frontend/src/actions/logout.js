import { LOGOUT } from  '../types/EventTypes';

export default function() {
    window.localStorage.removeItem('pro-stu-token');
    return {
        type: LOGOUT
    }
}