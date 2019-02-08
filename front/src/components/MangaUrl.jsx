import React from "react";
import styled from "styled-components";
import Button from "react-bootstrap/lib/Button";
import FormControl from "react-bootstrap/lib/FormControl";
import ControlLabel from "react-bootstrap/lib/ControlLabel";
import Panel from "react-bootstrap/lib/Panel";
import RandomColor from "randomcolor";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend
} from "recharts";

import CompareService from "../service/CompareService.jsx";

const PanelForm = styled.div`
  width: 400px;
  margin-left: auto;
  margin-right: auto;
`;
export default class MangaUrl extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      hide: ["name"],
      colors: [
        "#003f5c",
        "#2f4b7c",
        "#665191",
        "#a05195",
        "#d45087",
        "#f95d6a",
        "#ff7c43",
        "#ffa600",
        "#003f1d",
        "#2f4b1d",
        "#66511d",
        "#a0511d",
        "#d4501d",
        "#f95d1d",
        "#ff7c1d",
        "#ffa61d",
        "#003f7e",
        "#2f4b7e",
        "#66517e",
        "#a0517e",
        "#d4507e",
        "#f95d7e",
        "#ff7c7e",
        "#ffa67e"
      ]
    };

    this.handleChange = this.handleChange.bind(this);
    this.scrap = this.scrap.bind(this);
    this.dataFormater = this.dataFormater.bind(this);
  }

  handleChange(e) {
    this.setState({ url: e.target.value });
  }
  scrap() {
    CompareService.compare("flem").then(mangaGet => {
      console.log(mangaGet.data);
      this.setState({
        data: mangaGet.data
      });
    });
  }

  dataFormater(number) {
    if (number > 1000000000) {
      return (number / 1000000000).toString() + "B";
    } else if (number > 1000000) {
      return (number / 1000000).toString() + "M";
    } else if (number > 1000) {
      return (number / 1000).toString() + "K";
    } else {
      return number.toString();
    }
  }

  render() {
    return (
      <div>
        <Panel>
          <Button bsStyle="primary" onClick={this.scrap}>
            Flem !!!
          </Button>
          {this.state.data && (
            <LineChart
              width={1500}
              height={700}
              data={this.state.data}
              margin={{ top: 100, right: 30, left: 100, bottom: 5 }}
            >
              <XAxis dataKey="name" />
              <YAxis type="number" tickFormatter={this.dataFormater} />
              <CartesianGrid strokeDasharray="3 3" />
              <Tooltip
                formatter={value => new Intl.NumberFormat("en").format(value)}
              />
              <Legend onClick={data => console.log("data", data)} />
              {Object.keys(this.state.data[0]).map((key, i) => {
                if (!this.state.hide.includes(key)) {
                  return (
                    <Line
                      key={key}
                      type="linear"
                      dot={false}
                      dataKey={key}
                      stroke={this.state.colors[i]}
                      activeDot={{ r: 5 }}
                    />
                  );
                }
                return;
              })}
            </LineChart>
          )}
        </Panel>
      </div>
    );
  }
}
