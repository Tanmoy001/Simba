import { createStore,combineReducers,applyMiddleware } from '@reduxjs/toolkit';

import summaryReducer from './reducers/summaryReducer';
import {  userReducer } from './reducers/authReducer';
import { composeWithDevTools } from 'redux-devtools-extension';
import {thunk} from 'redux-thunk'; // 
 // example middleware 

 const rootReducer = combineReducers({
  user: userReducer,
  summary: summaryReducer
});

 const middleware = [thunk];

const store = createStore(
  rootReducer,
   composeWithDevTools(applyMiddleware(...middleware))
);

export default store;