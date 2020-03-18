import React from "react";
import Table from "react-bootstrap/Table";
import "./Post.css";

const post = props => (
  <article className="Post" onClick={props.clicked}>
    <h1>{props.name}</h1>
    <Table responsive>
      <thead>
        <tr>
          <th>Confirmed</th>
          <th>Recovered</th>
          <th>Deaths</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>{props.totalConfirmed}</td>
          <td>{props.totalRecovered}</td>
          <td>{props.totalDeath}</td>
        </tr>
      </tbody>
    </Table>
  </article>
);

export default post;
