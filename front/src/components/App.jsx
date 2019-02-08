import React from "react";
import Button from "react-bootstrap/lib/Button";
import FormGroup from "react-bootstrap/lib/FormGroup";
import Form from "react-bootstrap/lib/Form";
import FormControl from "react-bootstrap/lib/FormControl";
import ControlLabel from "react-bootstrap/lib/ControlLabel";
import Panel from "react-bootstrap/lib/Panel";
import styled from "styled-components";
import { observer } from "mobx-react";

import MangaUrl from "./MangaUrl.jsx";
import Cookies from "js-cookie";
import LoginForm from "./LoginForm.jsx";
import LoginStore from "../store/LoginStore.js";

const backdropStyle = {
  position: "fixed",
  zIndex: 1040,
  top: 0,
  bottom: 0,
  left: 0,
  right: 0,
  backgroundColor: "#000",
  opacity: 0.5
};

const modalStyle = function() {
  // we use some psuedo random coords so nested modals
  // don't sit right on top of each other.
  let top = 50;
  let left = 50;

  return {
    position: "fixed",
    width: 400,
    zIndex: 1040,
    top: top + "%",
    left: left + "%",
    border: "1px solid #e5e5e5",
    backgroundColor: "white",
    boxShadow: "0 5px 15px rgba(0,0,0,.5)",
    padding: 20
  };
};

@observer
export default class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      showModal: false
    };
    this.updateCookie = this.updateCookie.bind(this);
  }

  updateCookie() {
    LoginStore.refreshToken();
  }

  render() {
    return (
      <div style={{ textAlign: "center" }}>
        <h1>Welcome to Dofus Ladder</h1>
        {!LoginStore.token ? (
          <LoginForm login={this.updateCookie} />
        ) : (
          <MangaUrl />
        )}
      </div>
    );
  }
}
