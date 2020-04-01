import React, { Component } from "react";
import "./Home.css";
import Main from "../Main/Main";
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
            <GoHome className="Home" color="lightgreen" size="3rem" />
          </Link>

            <a href="https://www.buymeacoffee.com/esfe">
            <button type="button" className="btn btn-outline-warning" style={{position: 'absolute',top:'10px', right: '12px'}}>Buy us a coffee</button>
            </a>
        </header>
        <Route path="/" exact component={Main} />
        <Route path="/country/:id" exact component={CountryPost} />
        <Route path="/country/state/:id" exact component={StatePost} />
      </div>
    );
  }
}

export default Home;
