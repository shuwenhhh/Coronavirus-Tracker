import React, { useEffect, useState } from "react";
import {
  LineChart,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  Line,
  ResponsiveContainer
} from "recharts";

const data = [
  {
    name: "Page A",
    uv: 4000,
    pv: 2400,
    pu: 2200,
    amt: 2400
  },
  {
    name: "Page B",
    uv: 3000,
    pv: 1398,
    pu: 2200,

    amt: 2210
  },
  {
    name: "Page C",
    uv: 2000,
    pv: 10800,
    pu: 2200,
    amt: 2290
  },
  {
    name: "Page D",
    uv: 2780,
    pv: 3908,
    pu: 2200,

    amt: 2000
  },
  {
    name: "Page E",
    uv: 1890,
    pv: 4800,
    pu: 2200,
    amt: 2181
  },
  {
    name: "Page F",
    uv: 2390,
    pv: 3800,
    pu: 2200,
    amt: 2500
  },
  {
    name: "Page G",
    uv: 3490,
    pv: 4300,
    pu: 2200,
    amt: 2100
  }
];

function Lines() {
  return (
    <ResponsiveContainer width="100%" height={250}>
      <LineChart
        data={data}
        margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
      >
        <XAxis dataKey="name" />
        <YAxis />
        <CartesianGrid strokeDasharray="3 3" />
        <Tooltip />
        <Legend verticalAlign="top" height={36} />
        <Line
          name="pv of pages"
          type="monotone"
          dataKey="pv"
          stroke="#8884d8"
        />
        <Line
          name="uv of pages"
          type="monotone"
          dataKey="uv"
          stroke="#82ca9d"
        />
        <Line name="pu of pages" type="monotone" dataKey="pu" stroke="blue" />
        <Line name="amt of pages" type="monotone" dataKey="amt" stroke="red" />
      </LineChart>
    </ResponsiveContainer>
  );
}

export default Lines;
