export default class LoginController {

  constructor($mdDialog, $state, LoginService) {
    this.dialog = $mdDialog;
    this.state = $state;
    this.service = LoginService;
  }

	login(user) {
    this.service.login(user)
    .then((response) => {
        const { data, headers } = response;
        const token = headers("authorization");
        this.service.setCredentials(data, token);
        this.state.go("movies");
      })
      .catch((error) => {
        const { data } = error;
        if (data) {
          const { message } = data;
          const index = message.indexOf(":");
          const realMessage = message.substring(index +2);
          this.showDialog("Erro", realMessage);
        } else {
          this.showDialog("Erro", "Houve uma falha ao tentar realizar o login.");
          console.log(error);
        }
      });
  }

  showDialog(title, message) {
    const confirm = this.dialog.alert()
      .title(title)
      .textContent(message)
      .ariaLabel("Dialog")
      .ok("Ok");

    this.dialog.show(confirm);
  }
}