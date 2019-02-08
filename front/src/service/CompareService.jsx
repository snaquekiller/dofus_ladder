import { ConfigUriUtil } from "../config/index.jsx";

const endpoints = {
  compare: ConfigUriUtil.getEndpoint("/player/compare/graph")
};

export default {
  compare(name) {
    return ConfigUriUtil.get(endpoints.compare + "?name=" + name);
  }
};
