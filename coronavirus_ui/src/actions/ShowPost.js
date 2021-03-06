import axios from "axios";
export const SHOW_POST = "SHOW_POST";

export function ShowPost(country, cb) {
  const promise = axios.get(`/country/${country}`).then(res => {
    cb(res);
    return res;
  });
  return {
    type: SHOW_POST,
    payload: promise
  };
}
