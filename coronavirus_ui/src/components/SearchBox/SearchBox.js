import React from "react";

export default function SearchBox(props) {
  return (
    <div>
      <input type="text" onChange={props.handleInput} />
    </div>
  );
}
