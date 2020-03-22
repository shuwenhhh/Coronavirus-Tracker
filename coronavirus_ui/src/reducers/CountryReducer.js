import { SHOW_COUNTRY } from "../actions/ShowCountry";
let INIT_POSTS = {
  country: []
};

export default function CountryReducer(state = INIT_POSTS, action) {
  switch (action.type) {
    case SHOW_COUNTRY:
      return {
        ...state,
        country: action.country
      };
    default:
      return {
        ...state
      };
  }
}
