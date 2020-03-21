import React, { Component } from "react";
import axios from "axios";

import Lines from "../../components/legend/Lines";
import Title from "../../components/Title/Title";

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
        <Title
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
