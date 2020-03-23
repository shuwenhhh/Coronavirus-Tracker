import { combineReducers } from "redux";
import PostReducer from "./PostReducer";
import CountryReducer from "./CountryReducer";
import WorldReducer from "./WorldReducer";

const rootReducer = combineReducers({
  posts: PostReducer,
  country: CountryReducer,
  world:WorldReducer
});

export default rootReducer;
