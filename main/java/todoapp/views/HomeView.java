package todoapp.views;

import todoapp.commons.enums.UserRole;
import todoapp.commons.exceptions.PasswordNotMatchException;
import todoapp.commons.exceptions.UserNotExistException;
import todoapp.entities.User;
import todoapp.io.interfaces.InputReader;
import todoapp.io.interfaces.OutputWriter;
import todoapp.services.HomeService;

import static todoapp.commons.messages.StringMessages.*;

public class HomeView {

  private final HomeService homeService;

  public HomeView() {
    this.homeService = new HomeService();
  }

  public void getLogin(InputReader reader, OutputWriter writer) {

    String[] userDetails = new String[2];

    writer.writeOnNewLine(LOGIN);
    String username = null;
    while (username == null || username.trim().isEmpty()) {
      writer.writeLine(USERNAME);
      username = reader.readLine();
    }
    userDetails[0] = username;

    String password = null;
    while (password == null || password.trim().isEmpty()) {
      writer.writeLine(PASSWORD_LOGIN);
      password = reader.readLine();
    }
    userDetails[1] = password;
    User authorizedUser = null;

    try {
      authorizedUser = homeService.authorize(userDetails);
    } catch (PasswordNotMatchException | UserNotExistException e) {
      writer.writeOnNewLine(WRONG_CREDENTIALS);
      getLogin(reader, writer);
    }

    if (authorizedUser != null) {
      TaskView taskView = new TaskView(reader, writer, homeService.getTaskService(authorizedUser));

      if (authorizedUser.getUserRole().equals(UserRole.ADMIN)) {
        AdminView adminView =
            new AdminView(reader, writer, homeService.getAdminService(authorizedUser));
        adminView.setTaskView(taskView);
        adminView.start();
      }
      taskView.start();
    }
  }
}
