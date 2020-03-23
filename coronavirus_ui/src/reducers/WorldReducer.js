import { SHOW_WORLD } from "../actions/ShowWorld";

const INIT_POSTS = null;
export default function WorldReducer(oldState = INIT_POSTS, action) {
  switch (action.type) {
    case SHOW_WORLD:
      return action.payload;
    default:
      return oldState;
  }
}
