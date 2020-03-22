import React, { Component } from "react";
import axios from "axios";

import "./CountryPost.css";
import Title from "../../components/Title/Title";
import Lines from "../../components/legend/Lines";
import SearchBox from "../../components/SearchBox/SearchBox";
import StateList from "../CountryPost/StateList";

class CountryPost extends Component {
  state = {
    country: [],
    providences: [],
    query: ""
  };

  componentDidMount() {
    axios({
      method: "GET",
      url: "/country/" + this.props.match.params.id,
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      }
    }).then(response => {
      this.setState({ providences: response.data.states });
      this.setState({ country: response.data });
    });
  }
  postSelectedHandler = id => {
    this.setState({ selectedPostId: id });
  };
  handleInput = e => {
    this.setState({
      query: e.target.value
    });
  };

  render() {
    let filteredStates = this.state.providences.filter(providence => {
      return providence.state
        .toLowerCase()
        .includes(this.state.query.toLowerCase());
    });
    return (
      <div className="CountryPost">
        <Lines
          dailyList={this.state.country.dailyList}
          max={this.state.country.totalConfirmed}
        />
        <Title
          name={this.props.match.params.id}
          totalConfirmed={this.state.country.totalConfirmed}
          totalRecovered={this.state.country.totalRecovered}
          totalDeath={this.state.country.totalDeath}
        />
        {filteredStates.length >= 2 && (
          <SearchBox
            text="Search Providence/State Name Here"
            handleInput={this.handleInput}
          />
        )}
        <StateList providences={filteredStates} />
      </div>
    );
  }
}

export default CountryPost;
