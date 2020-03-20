import React, { Component } from "react";

import "./Home.css";
import Posts from "../Posts/Posts";
import CountryPost from "../CountryPost/CountryPost";
import { Route, Link } from "react-router-dom";
import StatePost from "../StatePost/StatePost";
import WorldMap from "../map/WorldMap";

class Home extends Component {

  render() {
    return (
      <div className="Home">
        <header>
          <nav>
            <ul>
              <li>
                <Link to="/">Home</Link>
              </li>
            </ul>
          </nav>
        </header>
        <Route path="/" exact component={WorldMap}/>
        <Route path="/" exact component={Posts} /> {/*world posts*/}
        <Route path="/country/:id" exact component={CountryPost} />
        <Route path="/country/state/:id" exact component={StatePost} />
      </div>
    );
  }
}

export default Home;
