import { applyMiddleware, combineReducers, createStore } from "redux";
import MessageReducer from "./components/MessageComponent/MessageSlice";
import SongReducer from "./components/SearchComponent/SongSlice";
const baseReducer =  combineReducers({  
    MessagePopup: MessageReducer,
    SongState: SongReducer
}) 
const store =  createStore(
    baseReducer,
    window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
    );


export default store;