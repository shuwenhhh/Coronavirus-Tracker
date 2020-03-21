import React from "react";
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

const Lines = props => {
  return (
    <ResponsiveContainer width="100%" height={300}>
      <LineChart
        data={props.dailyList}
        margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
      >
        <XAxis dataKey="date" />
        <YAxis domain={[0, props.max]} />
        <CartesianGrid strokeDasharray="3 3" />
        <Tooltip />
        <Legend verticalAlign="top" height={36} />
        <Line
          name="Confirmed"
          type="monotone"
          dataKey="confirmed"
          stroke="#8884d8"
        />
        <Line
          name="Recovered"
          type="monotone"
          dataKey="recovered"
          stroke="#82ca9d"
        />
        <Line name="Death" type="monotone" dataKey="death" stroke="red" />
      </LineChart>
    </ResponsiveContainer>
  );
};

export default Lines;
