import React, { Component } from "react";

import { connect } from "react-redux";
import ShowCountry from "../../actions/ShowCountry";
import ShowWorld from "../../actions/ShowWorld";
import Title from "../../components/Title/Title";
import "./Main.css";
import Lines from "../../components/legend/Lines";
import SearchBox from "../../components/SearchBox/SearchBox";
import CountryList from "./CountryList";
import WorldMap from "../WorldMap/WorldMap";

//world posts
class Main extends Component {
  state = {
    countries: [],
    worlds: [],
    query: ""
  };

  componentDidMount() {
    this.props.ShowCountry(res => {
      if (res.status === 200) {
        this.setState({ countries: res.data });
      } else {
        alert("error");
      }
    });
    this.props.ShowWorld(res => {
      if (res.status === 200) {
        this.setState({ worlds: res.data });
      } else {
        alert("error");
      }
    });
  }

  handleInput = e => {
    this.setState({
      query: e.target.value
    });
  };

  render() {
    let filteredCountry = this.state.countries.filter(country => {
      return country.countryName
        .toLowerCase()
        .includes(this.state.query.toLowerCase());
    });
    return (
      <React.Fragment>
        <div className="Main">
          <WorldMap />
          <Lines
            dailyList={this.state.worlds.dailyList}
            max={this.state.worlds.totalConfirmed}
          />

          <Title
            name="World"
            totalConfirmed={this.state.worlds.totalConfirmed}
            totalDeath={this.state.worlds.totalDeath}
            confirmedRate={this.state.worlds.confirmedRate}
            deathRate={this.state.worlds.deathRate}
          />
          <SearchBox
            text="Search Country Name Here"
            handleInput={this.handleInput}
          />
          <CountryList countries={filteredCountry} />
        </div>
        <p className="footer">
          All Data are from
          <a href="https://github.com/CSSEGISandData"> Johns Hopkins </a>
          Built by <a href="https://jhuilin.herokuapp.com/"> JianHui Lin </a>and
          <a href="https://github.com/shuwenhhh"> ShuWen Wang</a>
        </p>
      </React.Fragment>
    );
  }
}

export default connect(null, { ShowCountry, ShowWorld })(Main);
