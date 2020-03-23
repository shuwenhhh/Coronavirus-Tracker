import axios from "axios";
export const SHOW_POST = "SHOW_POST";

export function ShowPost(country, cb) {
  console.log(country);

  const promise = axios.get(`/country/${country}`)
   .then(res => {
    cb(res);
    console.log(res);
    return res;
  });
  return {
    type: SHOW_POST,
    payload: promise
  };
}
