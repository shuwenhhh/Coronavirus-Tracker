import React, { Component } from "react";

import Post from "../../components/Post/Post";
import FullPost from "../../components/FullPost/FullPost";
import axios from "axios";
import "./Blog.css";

class Blog extends Component {
  componentDidMount() {
    axios({
      method: "GET",
      url: "http://localhost:8080/allStates",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      }
    }).then(response => {
      console.log(response);
    });
  }
  render() {
    return (
      <div>
        <section className="Posts">
          <Post />
          <Post />
          <Post />
        </section>
        <section>
          <FullPost />
        </section>
      </div>
    );
  }
}

export default Blog;
