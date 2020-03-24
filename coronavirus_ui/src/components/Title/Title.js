import React from "react";
import "./Title.css";

const title = props => (
  <div className="country__item" onClick={props.clicked}>
    <h1 className="country__name">{props.name}</h1>
    <div className="country__item__data">
      <div className="country__item__data__item">
        <h1 className="country__text-1">Confirmed</h1>
        <h1 className="country__text-1">
          {props.totalConfirmed}
          {/* <span className="rate1">{props.confirmedRate}</span> */}
        </h1>
      </div>
      <div className="country__item__data__item">
        <h1 className="country__text-1">Recovered</h1>
        <h1 className="country__text-1">
          {props.totalRecovered}{" "}
          {/* <span className="rate2">{props.recoveredRate}</span> */}
        </h1>
      </div>
      <div className="country__item__data__item">
        <h1 className="country__text-1">Deaths</h1>
        <h1 className="country__text-1">
          {props.totalDeath}
          {/* {props.totalDeath} <span className="rate3">{props.deathRate}</span> */}
        </h1>
      </div>
    </div>
  </div>
);

export default title;
