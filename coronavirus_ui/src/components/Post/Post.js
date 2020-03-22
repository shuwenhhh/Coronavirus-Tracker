import React from "react";
import "./Post.css";

const post = props => (
  <div className="country__item" onClick={props.clicked}>
    <h1 className="country__name">{props.name}</h1>
    <div className="country__item__data">
      <div className="country__item__data__item">
        <h1 className="country__text-1">Confirmed</h1>
        <h1 className="country__text-1">{props.totalConfirmed}</h1>
      </div>
      <div className="country__item__data__item">
        <h1 className="country__text-1">Recovered</h1>
        <h1 className="country__text-1">{props.totalRecovered}</h1>
      </div>
      <div className="country__item__data__item">
        <h1 className="country__text-1">Deaths</h1>
        <h1 className="country__text-1">{props.totalDeath}</h1>
      </div>
    </div>
  </div>
);

export default post;
