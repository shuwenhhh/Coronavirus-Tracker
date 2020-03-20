import React, { Component } from "react";
import axios from "axios";

import Post from "../../components/Post/Post";
import Lines from "../../components/legend/Lines";

class StatePost extends Component {
  state = {
    posts: [],
    error: false
  };

  componentDidMount() {
    axios({
      method: "GET",
      url: "http://localhost:8080/country/state/" + this.props.match.params.id,
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      }
    }).then(response => {
      this.setState({ posts: response.data });
    });
  }

  render() {
    return (
      <div>
        <Lines
          dailyList={this.state.posts.dailyList}
          max={this.state.posts.totalConfirmed}
        />
        <Post
          name={this.props.match.params.id}
          totalConfirmed={this.state.posts.totalConfirmed}
          totalRecovered={this.state.posts.totalRecovered}
          totalDeath={this.state.posts.totalDeath}
        />
      </div>
    );
  }
}

export default StatePost;
