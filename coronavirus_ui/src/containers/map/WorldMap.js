import React, { Component } from "react";
import PropTypes from "prop-types";
import Datamaps from "datamaps";
import { connect } from "react-redux";
import { ShowPost } from "../../actions/ShowPost";
import "./style.css";

const MAP_CLEARING_PROPS = ["height", "scope", "setProjection", "width"];

const propChangeRequiresMapClear = (oldProps, newProps) => {
  return MAP_CLEARING_PROPS.some(key => oldProps[key] !== newProps[key]);
};

class WorldMap extends Component {
  static propTypes = {
    arc: PropTypes.array,
    arcOptions: PropTypes.object,
    bubbleOptions: PropTypes.object,
    bubbles: PropTypes.array,
    data: PropTypes.object,
    graticule: PropTypes.bool,
    height: PropTypes.any,
    labels: PropTypes.bool,
    responsive: PropTypes.bool,
    style: PropTypes.object,
    updateChoroplethOptions: PropTypes.object,
    width: PropTypes.any
  };

  constructor(props) {
    super(props);
    console.log(props);
    this.resizeMap = this.resizeMap.bind(this);
  }

  componentDidMount() {
    if (this.props.responsive) {
      window.addEventListener("resize", this.resizeMap);
    }
    this.drawMap();
  }

  componentDidUpdate(prevProps) {
    if (propChangeRequiresMapClear(prevProps, this.props)) {
      this.clear();
    }
    this.drawMap();
  }

  componentWillUnmount() {
    this.clear();
    if (this.props.responsive) {
      window.removeEventListener("resize", this.resizeMap);
    }
  }

  clear() {
    const { container } = this.refs;

    for (const child of Array.from(container.childNodes)) {
      container.removeChild(child);
    }

    delete this.map;
  }

  drawMap() {
    console.log("ddd");
    const {
      arc,
      arcOptions,
      bubbles,
      bubbleOptions,
      data,
      graticule,
      labels,
      updateChoroplethOptions,
      ...props
    } = this.props;

    let map = this.map;

    if (!map) {
      console.log("ss");
      map = this.map = new Datamaps({
        ...props,
        element: this.refs.container,
        done: function(dataMap) {
          dataMap.svg
            .selectAll(".datamaps-subunit")
            .on("click", function(geography) {
              console.log(props);
              const response = this.props.ShowPost(geography.properties.name);
              if (response.success) {
                alert(geography.properties.name);
                this.props.history.push("/");
              }
            });
        }
      });
    } else {
      map.updateChoropleth(data, updateChoroplethOptions);
    }

    if (arc) {
      map.arc(arc, arcOptions);
    }

    if (bubbles) {
      console.log(bubbles);
      map.bubbles(bubbles, bubbleOptions);
    }

    if (graticule) {
      map.graticule();
    }

    if (labels) {
      map.labels();
    }
  }

  resizeMap() {
    this.map.resize();
  }

  render() {
    const style = {
      height: window.innerHeight * 0.7,
      width: window.innerWidth,
      display: "block",
      marginLeft: "auto",
      marginRight: "auto",
      textAlign: "center",
      ...this.props.style
    };

    return <div ref="container" style={style} />;
  }
}

export default connect(null, { ShowPost })(WorldMap);
