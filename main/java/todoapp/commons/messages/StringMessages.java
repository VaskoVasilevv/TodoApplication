package todoapp.commons.messages;

public class StringMessages {

  public static final String WRONG_CREDENTIALS = "User Credentials Are Not Valid";

  public static final String GOOD_BYE = "Good bye!";

  public static final String ADMIN_WELCOME =
      "Admin Panel"
          + "\n"
          + "please Choose From The Following Options"
          + "\n"
          + "1) Create New User"
          + "\n"
          + "2) List All Users"
          + "\n"
          + "3) Edit User"
          + "\n"
          + "4) Delete User"
          + "\n"
          + "5) Task Management"
          + "\n"
          + "6) Exit";

  public static final String TASKS_WELCOME =
      "Please choose from the following options"
          + "\n"
          + "1) List all Tasks"
          + "\n"
          + "2) Create Task"
          + "\n"
          + "3) Edit Task"
          + "\n"
          + "4) Change Task Status"
          + "\n"
          + "5) Delete Task"
          + "\n"
          + "6) Share Task"
          + "\n"
          + "7) Exit";

  public static final String SELECT_CHOICE = "Please Enter Your Choice: ";

  public static final String INVALID_CHOICE = "Invalid Choice";

  public static final String SELECT_BETWEEN_NUMBERS = "Please Select Number Between [%d-%d]";

  public static final String NOT_VALID_INPUT_NUMBERS_ONLY = "Invalid Input Numbers Only";

  public static final String CREATE_NEW_USER = "Create New User:";

  public static final String USERNAME = "Please Enter Username: ";

  public static final String NEW_USER_PASSWORD = "Enter New Password: ";

  public static final String FIRST_NAME = "Enter User First Name: ";

  public static final String LAST_NAME = "Enter User Last Name: ";

  public static final String SET_USER_ROLE = "Set User Role [ADMIN:USER]: ";

  public static final String USER_SUCCESSFULLY_ADD = "User %s Successfully Added";

  public static final String DELETE_USER = "Delete User by ID";

  public static final String SELECT_ID = "Select ID: ";

  public static final String EDIT_USER_MENU = "Edit User:";

  public static final String TASK_HEADER_FORMAT = "|%-5s|%20s|%10s|%10s|%15s|%10s|%5s|%5s|%20s|";

  public static final String CHANGE_TASK_STATUS = "Status: COMPLETED | IN PROGRESS [1/2]: ";

  public static final String LOGIN = "Please Login";

  public static final String PASSWORD_LOGIN = "Please Enter Password: ";

  public static final String USER_NOT_EXIST = "User With %d ID Not Exist";

  public static final String CHANGE_USERNAME = "Change User [%s] Username? (Y/N): ";

  public static final String CHANGE_FIRST_NAME = "Change User First Name? [%s] (Y/N): ";

  public static final String CHANGE_LAST_NAME = "Change User Last Name? [%s] (Y/N): ";

  public static final String CHANGE_USER_ROLE = "Change User Role [%s] (Y/N): ";

  public static final String TASK_HEADER = String.format(
          TASK_HEADER_FORMAT,
          "ID",
          "NAME",
          "CREATED ON",
          "CREATED BY USER ID",
          "STATUS",
          "MODIFIED ON",
          "MODIFIED BY",
          "SHARED",
          "SHARED TO");

  public static final String USERS_PRINT_HEADER = String.format("|%s|%15s|", "User ID", "Username");

  public static final String USER_PRINT_FORMAT = "|%-7s|%15s|";

  public static final String YES = "y";

  public static final String INPUT_CANNOT_BE_EMPTY = "INPUT CANNOT BE EMPTY";
}
