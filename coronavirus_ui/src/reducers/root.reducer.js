import { combineReducers } from "redux";
import PostReducer from "./PostReducer";
import CountryReducer from "./CountryReducer";
import WorldReducer from "./WorldReducer";
import ProvidenceReducer from "./ProvidenceReducer";

const rootReducer = combineReducers({
  posts: PostReducer,
  country: CountryReducer,
  world: WorldReducer,
  providence: ProvidenceReducer
});

export default rootReducer;
