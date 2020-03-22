import axios from "axios";

export const SHOW_COUNTRY = "SHOW_COUNTRY";

export default function ShowCountry() {
  const promise = axios({
    method: "GET",
    url: "/allCountries",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    }
  }).then(response => {
    return response.data;
  });
  return {
    type: SHOW_COUNTRY,
    country: promise
  };
}
