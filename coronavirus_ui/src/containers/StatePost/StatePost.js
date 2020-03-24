import React, { Component } from "react";

import Lines from "../../components/legend/Lines";
import Title from "../../components/Title/Title";
import { ShowProvidence } from "../../actions/ShowProvidence";
import { connect } from "react-redux";

class StatePost extends Component {
  state = {
    posts: [],
    error: false
  };

  componentDidMount() {
    this.props.ShowProvidence(this.props.match.params.id, res => {
      if (res.status === 200) {
        this.setState({ posts: res.data });
      } else {
        alert("error");
      }
    });
  }
  render() {
    return (
      <div>
        <Lines
          dailyList={this.state.posts.dailyList}
          max={this.state.posts.totalConfirmed}
        />
        <Title
          name={this.props.match.params.id}
          totalConfirmed={this.state.posts.totalConfirmed}
          totalRecovered={this.state.posts.totalRecovered}
          totalDeath={this.state.posts.totalDeath}
          confirmedRate={this.state.posts.confirmedRate}
          recoveredRate={this.state.posts.recoveredRate}
          deathRate={this.state.posts.deathRate}
        />
      </div>
    );
  }
}

export default connect(null, { ShowProvidence })(StatePost);
