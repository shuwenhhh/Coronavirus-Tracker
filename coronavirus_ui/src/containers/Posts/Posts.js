import React, { Component } from "react";

import Post from "../../components/Post/Post";
import axios from "axios";
import "./Posts.css";
import { Link } from "react-router-dom";
import Lines from "../../components/legend/Lines";
//world posts
class Posts extends Component {
  state = {
    posts: [],
    worlds: [],
    selectedPostId: null,
    error: false
  };

  componentDidMount() {
    axios({
      method: "GET",
      url: "http://localhost:8080/allCountries",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      }
    }).then(response => {
      this.setState({ posts: response.data });
    });

    axios({
      method: "GET",
      url: "http://localhost:8080/world",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      }
    }).then(response => {
      this.setState({ worlds: response.data });
    });
  }

  postSelectedHandler = id => {
    this.setState({ selectedPostId: id });
  };
  render() {
    let posts = <p style={{ textAlign: "center" }}>Something went wrong</p>;
    if (!this.state.error) {
      posts = this.state.posts.map(post => {
        return (
          <Link to={"country/" + post.countryName} key={post.countryName}>
            <Post
              name={post.countryName}
              totalConfirmed={post.totalConfirmed}
              totalRecovered={post.totalRecovered}
              totalDeath={post.totalDeath}
              clicked={() => this.postSelectedHandler(post.countryName)}
            />
          </Link>
        );
      });
    }
    return (
      <div>
        <Lines
          dailyList={this.state.worlds.dailyList}
          max={this.state.worlds.totalConfirmed}
        />
        <Post
          name="World"
          totalConfirmed={this.state.worlds.totalConfirmed}
          totalRecovered={this.state.worlds.totalRecovered}
          totalDeath={this.state.worlds.totalDeath}
        />
        {posts}
      </div>
    );
  }
}

export default Posts;
