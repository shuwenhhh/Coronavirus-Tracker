import React, { Component } from "react";

import Post from "../../components/Post/Post";
import axios from "axios";
import "./Blog.css";
import Lines from "../../components/legend/Lines";

class Blog extends Component {
  state = {
    posts: [],
    worlds: []
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
      console.log(response.data);
      this.setState({ worlds: response.data });
    });
  }

  postSelectedHandler = id => {
    console.log(id);
  };
  render() {
    const posts = this.state.posts.map(post => {
      return (
        <Post
          key={post.countryName}
          name={post.countryName}
          totalConfirmed={post.totalConfirmed}
          totalRecovered={post.totalRecovered}
          totalDeath={post.totalDeath}
          clicked={() => this.postSelectedHandler(post.countryName)}
        />
      );
    });
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

export default Blog;
