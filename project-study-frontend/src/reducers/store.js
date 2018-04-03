import { combineReducers, createStore, applyMiddleware  } from 'redux';
import promiseMiddleware from 'redux-promise';
import UserReducer from './UserReducer';
import TokenCheckReducer from './TokenCheckReducer';

const reducer = combineReducers({
	user: UserReducer,
	tokenChecked: TokenCheckReducer
})

const store = createStore(
	reducer,
 	window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__(),
 	applyMiddleware(promiseMiddleware)
)

export default store;