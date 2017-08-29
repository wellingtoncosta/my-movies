export default class AuthInterceptor {

  request(config) {
    const { url } = config;

    if (!url.endsWith("/login")) {
      const token = localStorage.getItem("token");
      config.headers = config.headers || {};
      config.headers.Authorization = token;
    }

    return config;
  }
}