package todoapp.views;

import at.favre.lib.crypto.bcrypt.BCrypt;
import todoapp.commons.enums.UserRole;
import todoapp.commons.exceptions.UserExistException;
import todoapp.commons.exceptions.UserNotExistException;
import todoapp.io.interfaces.InputReader;
import todoapp.io.interfaces.OutputWriter;
import todoapp.services.AdminService;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static todoapp.commons.messages.StringMessages.*;

public class AdminView {

  private final InputReader reader;
  private final AdminService service;
  private final OutputWriter writer;
  private TaskView taskView;

  public AdminView(InputReader reader, OutputWriter writer, AdminService adminService) {
    this.reader = reader;
    this.writer = writer;
    this.service = adminService;
  }

  public void start() {
    writer.writeOnNewLine(ADMIN_WELCOME);
    writer.writeLine(SELECT_CHOICE);

    switch (getChoice()) {
      case 1:
        createUser();
        break;
      case 2:
        listAllUsers();
        start();
        break;
      case 3:
        editUser();
        break;
      case 4:
        deleteUser();
        break;
      case 5:
        taskManagement();
        break;
      case 6:
        exit();
        break;
    }
  }

  private void exit() {
    writer.writeOnNewLine(GOOD_BYE);
    service.exit();
  }

  private void taskManagement() {
    taskView.start();
  }

  private void deleteUser() {

    listAllUsers();

    writer.writeOnNewLine(DELETE_USER);
    writer.writeLine(SELECT_ID);

    Long id = null;

    try {
      id = Long.parseLong(reader.readLine());
    } catch (NumberFormatException e) {
      writer.writeOnNewLine(NOT_VALID_INPUT_NUMBERS_ONLY);
      deleteUser();
    }

    service.deleteUser(id);
    start();
  }

  private void editUser() {

    listAllUsers();

    Long userID = null;

    writer.writeOnNewLine(EDIT_USER_MENU);
    writer.writeLine(SELECT_ID);

    try {
      userID = Long.parseLong(reader.readLine());
    } catch (NumberFormatException e) {
      writer.writeLine(NOT_VALID_INPUT_NUMBERS_ONLY);
    }

    List<String> userDetails = null;
    try {
      userDetails = service.getUserDetails(userID);
    } catch (UserNotExistException e) {
      writer.writeOnNewLine(String.format(USER_NOT_EXIST, userID));
      editUser();
    }

    assert userDetails != null;
    writer.writeLine(String.format(CHANGE_USERNAME, userDetails.get(0)));
    if (reader.readLine().equalsIgnoreCase(YES)) {
      String username = null;

      while (username == null || username.trim().isEmpty()) {
        writer.writeLine(USERNAME);
        username = reader.readLine();
      }
      userDetails.set(0, username);
    }
    writer.writeLine(String.format(CHANGE_FIRST_NAME, userDetails.get(1)));
    if (reader.readLine().equalsIgnoreCase(YES)) {
      String firstName = null;
      while (firstName == null || firstName.trim().isEmpty()) {
        writer.writeLine(FIRST_NAME);
        firstName = reader.readLine();
      }
      userDetails.set(1, firstName);
    }

    writer.writeLine(String.format(CHANGE_LAST_NAME, userDetails.get(2)));
    if (reader.readLine().equalsIgnoreCase(YES)) {
      String lastName = null;
      while (lastName == null || lastName.trim().isEmpty()) {
        writer.writeLine(LAST_NAME);
        lastName = reader.readLine();
      }
      userDetails.set(2, lastName);
    }

    writer.writeLine(String.format(CHANGE_USER_ROLE, userDetails.get(3)));

    if (reader.readLine().equalsIgnoreCase(YES)) {
      if (userDetails.get(3).equals(UserRole.ADMIN.name())) {
        userDetails.set(3, UserRole.USER.name());
      } else {
        userDetails.set(3, UserRole.ADMIN.name());
      }
    }
    service.editUser(userID, userDetails);
    start();
  }

  private void listAllUsers() {
    Map<Long, String> listOfUsers = service.getListOfUsers();

    StringBuilder output = new StringBuilder();
    output
        .append(USERS_PRINT_HEADER).append(System.lineSeparator())
        .append("-".repeat(USERS_PRINT_HEADER.length()))
        .append(System.lineSeparator());

    for (Map.Entry<Long, String> entry : listOfUsers.entrySet()) {
      output.append(String.format(USER_PRINT_FORMAT, entry.getKey(), entry.getValue()));
      output.append(System.lineSeparator());
    }
    writer.writeOnNewLine(output.toString());
  }

  private void createUser() {

    writer.writeOnNewLine(CREATE_NEW_USER);
    writer.writeLine(USERNAME);
    String username = stringValidation(reader.readLine());
    writer.writeLine(NEW_USER_PASSWORD);
    String password =
        BCrypt.withDefaults().hashToString(6, stringValidation(reader.readLine()).toCharArray());
    writer.writeLine(FIRST_NAME);
    String firstName = stringValidation(reader.readLine());
    writer.writeLine(LAST_NAME);
    String lastName = stringValidation(reader.readLine());

    writer.writeLine(SET_USER_ROLE);
    String role = stringValidation(reader.readLine()).toUpperCase(Locale.ROOT);

    try {

      if (service.createNewUser(username, password, firstName, lastName, role)) {

        writer.writeLine(String.format(USER_SUCCESSFULLY_ADD, username));
        writer.writeOnNewLine(System.lineSeparator());
        start();
      }
    } catch (UserExistException e) {
      writer.writeOnNewLine(e.getMessage());
      start();
    }
  }

  private String stringValidation(String readLine) {

    while (readLine == null || readLine.trim().isEmpty()) {
      writer.writeOnNewLine(INPUT_CANNOT_BE_EMPTY);
    }
    return readLine;
  }

  private int getChoice() {
    int choice = 0;
    try {
      choice = Integer.parseInt(reader.readLine());
      if (choice < 1 || choice > 6) {
        writer.writeOnNewLine(String.format(SELECT_BETWEEN_NUMBERS, 1, 6));
        start();
      }
    } catch (NumberFormatException e) {
      writer.writeOnNewLine(NOT_VALID_INPUT_NUMBERS_ONLY);
      start();
    }
    return choice;
  }

  public void setTaskView(TaskView taskView) {
    this.taskView = taskView;
  }
}
