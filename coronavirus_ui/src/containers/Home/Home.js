import React, { Component } from "react";
import "./Home.css";
import Posts from "../Posts/Posts";
import CountryPost from "../CountryPost/CountryPost";
import { Route, Link } from "react-router-dom";
import StatePost from "../StatePost/StatePost";
import { GoHome } from "react-icons/go";

class Home extends Component {
  render() {
    return (
      <div>
        <header>
          <Link to="/">
            <GoHome color="lightgreen" size="3rem" />
          </Link>
        </header>
        {/* <Route path="/" exact component={WorldMap} /> */}
        <Route path="/" exact component={Posts} />
        <Route path="/country/:id" exact component={CountryPost} />
        <Route path="/country/state/:id" exact component={StatePost} />
      </div>
    );
  }
}

export default Home;
