import React from "react";
import { BrowserRouter } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import Home from "./containers/Home/Home";

function App() {
  return (
    <BrowserRouter>
      <div>
        <Home />
      </div>
    </BrowserRouter>
  );
}

export default App;
