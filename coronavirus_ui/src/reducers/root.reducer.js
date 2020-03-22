import { combineReducers } from "redux";
import PostReducer from "./PostReducer";
import CountryReducer from "./CountryReducer";

const rootReducer = combineReducers({
  posts: PostReducer,
  country: CountryReducer
});

export default rootReducer;
