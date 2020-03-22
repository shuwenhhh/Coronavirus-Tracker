import React from "react";
import ReactDom from "react-dom";
import App from "./App";
import "bootstrap/dist/css/bootstrap.min.css";
import { applyMiddleware, compose, createStore } from "redux";
import rootReducer from "./reducers/root.reducer";
import { Provider } from "react-redux";
// import reduxPromise from "redux-promise";
import thunk from "redux-thunk";
import axios from "axios";

axios.defaults.baseURL = "http://localhost:8080";

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
const store = createStore(
  rootReducer,
  composeEnhancers(applyMiddleware(thunk))
);

ReactDom.render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.querySelector("#root")
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
