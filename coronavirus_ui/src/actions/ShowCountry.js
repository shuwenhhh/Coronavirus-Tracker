import axios from "axios";

export const SHOW_COUNTRY = "SHOW_COUNTRY";

export default function ShowCountry(cb) {
  const promise = axios.get("/allCountries").then(res=>{
    cb(res);
    return res;
  })
  return {
    type: SHOW_COUNTRY,
    payload: promise
  };
}
