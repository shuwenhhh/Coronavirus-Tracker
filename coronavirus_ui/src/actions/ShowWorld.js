import axios from "axios";

export const SHOW_WORLD = "SHOW_WORLD";

export default function ShowWorld(cb) {
    const promise = axios.get("/world").then(res=>{
        cb(res);
        return res;
    })
    return {
        type: SHOW_WORLD,
        payload: promise
    };
}
