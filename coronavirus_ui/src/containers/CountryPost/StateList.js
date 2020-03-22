import React, { Component } from "react";
import Post from "../../components/Post/Post";
import { Link } from "react-router-dom";

class StateList extends Component {
  state = {
    selectedPostId: null
  };
  postSelectedHandler = id => {
    this.setState({ selectedPostId: id });
  };
  render() {
    let providences = this.props.providences.map(providence => {
      return (
        <Link to={"state/" + providence.state} key={providence.state}>
          <Post
            name={providence.state}
            totalConfirmed={providence.totalConfirmed}
            totalRecovered={providence.totalRecovered}
            totalDeath={providence.totalDeath}
            clicked={() => this.postSelectedHandler(providence.state)}
          />
        </Link>
      );
    });
    return <React.Fragment>{providences}</React.Fragment>;
  }
}

export default StateList;
