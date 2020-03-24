import React, { Component } from "react";
import ShowAllStates from "../../actions/ShowAllStates";
import { connect } from "react-redux";
import { Map, CircleMarker, TileLayer, Tooltip } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import "./WorldMap.css";

class WorldMap extends Component {
  state = {
    providences: []
  };
  componentDidMount() {
    this.props.ShowAllStates(res => {
      if (res.status === 200) {
        this.setState({ providences: res.data });
      } else {
        alert("error");
      }
    });
  }

  render() {
    return (
      <Map
        style={{ height: "480px", width: "100%" }}
        zoom={2}
        center={[-0.09, 51.505]}
      >
        <TileLayer url="http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />

        {this.state.providences.map((city, index) => {
          return (
            <CircleMarker
              key={index}
              center={[city.latitude, city.longitude]}
              radius={2 * Math.log(city.totalConfirmed / 75)}
              fillOpacity={0.5}
              stroke={false}
              color="red"
            >
              <Tooltip direction="right" offset={[-8, -2]} opacity={2}>
                <span className="n">{city.country}</span>
                {city.state !== "UNDEF" ? (
                  <span className="n">{", " + city.state}</span>
                ) : null}
                <br />
                <span className="c">{"Confirmed: " + city.totalConfirmed}</span>
                <br />
                <span className="r">
                  {"Reconvered: " + city.totalRecovered}
                </span>
                <br />
                <span className="d">{"Deaths: " + city.totalDeath}</span>
              </Tooltip>
            </CircleMarker>
          );
        })}
      </Map>
    );
  }
}

export default connect(null, { ShowAllStates })(WorldMap);
