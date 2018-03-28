import { combineReducers, createStore } from 'redux';
import UserReducer from './UserReducer';

const reducer = combineReducers({
	user: UserReducer
})

const store = createStore(
 reducer,
)

export default store;