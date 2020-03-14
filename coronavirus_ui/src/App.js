import React from "react";
import "./App.css";
import Lines from "../src/components/legend/Lines";
import Blog from "./containers/Blog/Blog";

function App() {
  return (
    <React.Fragment>
      <Lines />
      <Blog />
    </React.Fragment>
  );
}

export default App;
