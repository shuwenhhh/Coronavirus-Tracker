import React from "react";
import "./Post.css";

const post = props => (
  <div className="country__item" onClick={props.clicked}>
    <h1 className="country__name">{props.name}</h1>
    <div className="country__item__data">
      <div className="country__item__data__item">
        <h1 className="country__text-1">Confirmed</h1>
        <h1 className="country__text-1">
          {props.totalConfirmed}
          {props.confirmedRate > 0 && (
            <span className="rate">{" +" + props.confirmedRate}</span>
          )}
        </h1>
      </div>
      <div className="country__item__data__item">
        <h1 className="country__text-1">Deaths</h1>
        <h1 className="country__text-1">
          {props.totalDeath}
          {props.deathRate > 0 && (
            <span className="rate">{" +" + props.deathRate}</span>
          )}
        </h1>
      </div>
      <div className="country__item__data__item">
        <h1 className="country__text-1">Fatality Rate</h1>
        <h1 className="country__text-1">
          {((props.totalDeath * 100) / props.totalConfirmed).toFixed(3) + "%"}
        </h1>
      </div>
    </div>
  </div>
);

export default post;
