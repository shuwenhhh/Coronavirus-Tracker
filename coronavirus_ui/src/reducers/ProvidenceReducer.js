import { SHOW_PROVIDENCE } from "../actions/ShowProvidence";
let INIT_POSTS = {
  providence: []
};

export default function ProvidenceReducer(state = INIT_POSTS, action) {
  switch (action.type) {
    case SHOW_PROVIDENCE:
      return {
        ...state,
        providence: action.providence
      };
    default:
      return {
        ...state
      };
  }
}
