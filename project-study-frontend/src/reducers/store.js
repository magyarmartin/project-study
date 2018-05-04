import { combineReducers, createStore, applyMiddleware  } from 'redux';
import reduxThunk from 'redux-thunk';
import UserReducer from './UserReducer';
import TokenCheckReducer from './TokenCheckReducer';
import RegistrationReducer from './RegistrationReducer';
import UserDetailReducer from './UserDetailReducer';

const reducer = combineReducers({
	user: UserReducer,
	tokenChecked: TokenCheckReducer,
	registration: RegistrationReducer,
	userDetail: UserDetailReducer
})

const store = createStore(
	reducer,
 	window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__(),
 	applyMiddleware(reduxThunk)
)

export default store;