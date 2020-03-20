import React from "react";
import { BrowserRouter } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import Home from "./containers/Home/Home";

function App() {
  return (
    <BrowserRouter>
      <React.Fragment>
        <Home />
      </React.Fragment>
    </BrowserRouter>
  );
}

export default App;
