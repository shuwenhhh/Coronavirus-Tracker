import React, { Component } from "react";

import { connect } from "react-redux";
import ShowCountry from "../../actions/ShowCountry";
import ShowWorld from "../../actions/ShowWorld";
import Title from "../../components/Title/Title";
import axios from "axios";
import "./Main.css";
import Lines from "../../components/legend/Lines";
import WorldMap from "../map/WorldMap";
import SearchBox from "../../components/SearchBox/SearchBox";
import CountryList from "./CountryList";

//world posts
class Main extends Component {
  state = {
    countries: [],
    worlds: [],
    query: ""
  };

  componentDidMount() {
    this.props.ShowCountry((res) => {
      console.log(res);
      if (res.status === 200) {
        this.setState({posts: res.data});
      } else {
        alert('error');
      }
    })
    this.props.ShowWorld((res) => {
      console.log(res);
      if (res.status === 200) {
        this.setState({worlds: res.data});
      } else {
        alert('error');
      }
    })
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
      <div className="Main">
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
        <SearchBox
          text="Search Country Name Here"
          handleInput={this.handleInput}
        />
        <CountryList countries={filteredCountry} />
      </div>
    );
  }
}

// const mapStateToProps = state => {
//   return {
//     country: state.country
//   };
// };

// export default connect(mapStateToProps, ShowCountry)(Posts);
export default connect(null,{ShowCountry,ShowWorld})(Main);
