import { observable, action, computed } from "mobx";
import Cookies from "js-cookie";

const LoginStore = {
  @observable token: Cookies.get("token"),

  @action
  refreshToken() {
    LoginStore.token = Cookies.get("token");
  }
};

export default LoginStore;
