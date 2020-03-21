import React, { Component } from "react";
import axios from "axios";

import "./CountryPost.css";
import Post from "../../components/Post/Post";
import Title from "../../components/Title/Title";
import Lines from "../../components/legend/Lines";
import { Link } from "react-router-dom";

class CountryPost extends Component {
  state = {
    posts: [],
    providences: [],
    selectedPostId: null,
    error: false
  };

  componentDidMount() {
    axios({
      method: "GET",
      url: "http://localhost:8080/country/" + this.props.match.params.id,
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      }
    }).then(response => {
      this.setState({ providences: response.data.states });
      this.setState({ posts: response.data });
    });
  }
  postSelectedHandler = id => {
    this.setState({ selectedPostId: id });
  };

  render() {
    let providences = (
      <p style={{ textAlign: "center" }}>Something went wrong</p>
    );
    if (!this.state.error) {
      providences = this.state.providences.map(providence => {
        return (
          <Link to={"state/" + providence.state} key={providence.state}>
            <Post
              name={providence.state}
              totalConfirmed={providence.totalConfirmed}
              totalRecovered={providence.totalRecovered}
              totalDeath={providence.totalDeath}
              clicked={() => this.postSelectedHandler(providence.state)}
            />
          </Link>
        );
      });
    }
    return (
      <div className="CountryPost">
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
        {providences}
      </div>
    );
  }
}

export default CountryPost;
