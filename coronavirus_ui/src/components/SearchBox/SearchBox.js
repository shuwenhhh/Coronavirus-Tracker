import React from "react";
import "./SearchBox.css";

export default function SearchBox(props) {
  return (
    <div className="search-box">
      <input
        className="search-text"
        type="text"
        onChange={props.handleInput}
        placeholder={props.text}
      />
    </div>
  );
}
