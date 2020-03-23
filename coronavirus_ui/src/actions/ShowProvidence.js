import axios from "axios";
export const SHOW_PROVIDENCE = "SHOW_PROVIDENCE";

export function ShowProvidence(providence, cb) {
  const promise = axios.get(`/country/state/${providence}`).then(res => {
    cb(res);
    return res;
  });
  return {
    type: SHOW_PROVIDENCE,
    payload: promise
  };
}
