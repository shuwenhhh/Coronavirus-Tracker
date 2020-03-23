import axios from "axios";

export const SHOW_ALLSTATES = "SHOW_ALLSTATES";

export default function ShowAllStates(cb) {
  const promise = axios.get("/allStates").then(res => {
    cb(res);
    return res;
  });
  return {
    type: SHOW_ALLSTATES,
    payload: promise
  };
}
