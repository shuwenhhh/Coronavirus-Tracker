import React, { Component } from "react";

import { connect } from "react-redux";
import ShowCountry from "../../actions/ShowCountry";
import Post from "../../components/Post/Post";
import Title from "../../components/Title/Title";
import axios from "axios";
import "./Posts.css";
import { Link } from "react-router-dom";
import Lines from "../../components/legend/Lines";
import WorldMap from "../map/WorldMap";
import SearchBox from "../../components/SearchBox/SearchBox";

//world posts
class Posts extends Component {
  state = {
    posts: [],
    worlds: [],
    selectedPostId: null,
    searchPost: ""
  };

  componentDidMount() {
    axios({
      method: "GET",
      url: "/allCountries",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      }
    }).then(response => {
      this.setState({ posts: response.data });
    });

    axios({
      method: "GET",
      url: "/world",
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

  handleInput = e => {
    this.setState({
      searchPost: e.target.value
    });
  };

  render() {
    // console.log(this.props.country);
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
      <div className="Posts">
        <WorldMap />
        <Lines
          dailyList={this.state.worlds.dailyList}
          max={this.state.worlds.totalConfirmed}
        />
        <Title
          name="World"
          totalConfirmed={this.state.worlds.totalConfirmed}
          totalRecovered={this.state.worlds.totalRecovered}
          totalDeath={this.state.worlds.totalDeath}
        />
        {/* <SearchBox handleInput={this.handleInput} /> */}
        {posts}
      </div>
    );
  }
}

const mapStateToProps = state => {
  return {
    country: state.country
  };
};

export default connect(mapStateToProps, ShowCountry)(Posts);
