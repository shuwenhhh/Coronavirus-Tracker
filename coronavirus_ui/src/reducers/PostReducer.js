import {SHOW_POST} from "../actions/ShowPost";


const INIT_POSTS= null;
export default function PostReducer(oldState=INIT_POSTS,action) {
    switch (action.type) {
        case SHOW_POST:
            console.log(action.payload)
            return action.payload;
        default:
            return oldState;
    }
}
