import React, { Component } from "react";
import Post from "../../components/Post/Post";
import { Link } from "react-router-dom";

class CountryList extends Component {
  state = {
    selectedPostId: null
  };
  postSelectedHandler = id => {
    this.setState({ selectedPostId: id });
  };
  render() {
    let countries = this.props.countries.map(country => {
      return (
        <Link to={"country/" + country.countryName} key={country.countryName}>
          <Post
            name={country.countryName}
            totalConfirmed={country.totalConfirmed}
            totalRecovered={country.totalRecovered}
            totalDeath={country.totalDeath}
            clicked={() => this.postSelectedHandler(country.countryName)}
          />
        </Link>
      );
    });
    return <React.Fragment>{countries}</React.Fragment>;
  }
}

export default CountryList;
