import React from "react";
import "./Title.css";

const title = props => (
  <div class="country__item" onClick={props.clicked}>
    <h1 class="country__name">{props.name}</h1>
    <div class="country__item__data">
      <div class="country__item__data__item">
        <h1 class="country__text-1">{props.totalConfirmed}</h1>
        <h1 class="country__text-1">Confirmed</h1>
      </div>
      <div class="country__item__data__item">
        <h1 class="country__text-1">{props.totalDeath}</h1>
        <h1 class="country__text-1">Deaths</h1>
      </div>
      <div class="country__item__data__item">
        <h1 class="country__text-1">{props.totalRecovered}</h1>
        <h1 class="country__text-1">Recovered</h1>
      </div>
    </div>
  </div>
);

export default title;
