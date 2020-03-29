import React, { Component } from "react";
import "./CountryPost.css";
import Title from "../../components/Title/Title";
import Lines from "../../components/legend/Lines";
import SearchBox from "../../components/SearchBox/SearchBox";
import StateList from "../CountryPost/StateList";
import { ShowPost } from "../../actions/ShowPost";
import { connect } from "react-redux";
class CountryPost extends Component {
  state = {
    country: [],
    providences: [],
    query: ""
  };

  componentDidMount() {
    this.props.ShowPost(this.props.match.params.id, res => {
      if (res.status === 200) {
        this.setState({ providences: res.data.states });
        this.setState({ country: res.data });
        console.log(this.state.country);
      } else {
        alert("error");
      }
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
          totalDeath={this.state.country.totalDeath}
          confirmedRate={this.state.country.confirmedRate}
          deathRate={this.state.country.deathRate}
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

export default connect(null, { ShowPost })(CountryPost);
