import { SHOW_ALLSTATES } from "../actions/ShowAllStates";

const INIT_POSTS = null;
export default function AllStatesReducer(oldState = INIT_POSTS, action) {
  switch (action.type) {
    case SHOW_ALLSTATES:
      return action.payload;
    default:
      return oldState;
  }
}
