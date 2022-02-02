package todoapp.views;

import todoapp.commons.enums.TaskStatus;
import todoapp.entities.Task;
import todoapp.io.interfaces.InputReader;
import todoapp.io.interfaces.OutputWriter;
import todoapp.services.TaskService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static todoapp.commons.messages.StringMessages.*;

public class TaskView {

  private InputReader reader;
  private OutputWriter writer;

  private TaskService taskService;

  public TaskView(InputReader reader, OutputWriter writer, TaskService taskService) {

    this.reader = reader;
    this.writer = writer;
    this.taskService = taskService;
  }

  public TaskView(TaskService taskService) {}

  public void start() {

    writer.writeOnNewLine(TASKS_WELCOME);
    writer.writeLine(SELECT_CHOICE);
    switch (getChoice()) {
      case 1:
        listAllTasks();
        start();
        break;
      case 2:
        createTask();
        break;
      case 3:
        editTask();
        break;
      case 4:
        changeStatus();
        break;
      case 5:
        deleteTask();
        break;
      case 6:
        shareTask();
        break;
      case 7:
        exit();

        break;
    }
  }

  private void exit() {
    writer.writeOnNewLine(GOOD_BYE);
    taskService.exit();
  }

  private void changeStatus() {
    listAllTasks();

    String[] taskInfo = new String[2];

    while (taskInfo[0] == null || taskInfo[0].trim().isEmpty()) {
      writer.writeLine(SELECT_ID);
      taskInfo[0] = reader.readLine();
    }

    writer.writeLine(CHANGE_TASK_STATUS);
    if (reader.readLine().equals("1")) {
      taskInfo[1] = TaskStatus.COMPLETED.name();
    } else {
      taskInfo[1] = TaskStatus.IN_PROGRESS.name();
    }
    taskService.changeStatus(taskInfo[0], taskInfo[1]);
    start();
  }

  private void shareTask() {
    listAllTasks();
    writer.writeLine("Please select task id to share: ");
    String taskId = null;
    taskId = reader.readLine();

    Map<Long, String> listOfUsers = taskService.listUsers();
    StringBuilder output = new StringBuilder().append(System.lineSeparator());
    output.append(String.format("|%s|%15s|", "User ID", "Username")).append(System.lineSeparator());
    for (Map.Entry<Long, String> entry : listOfUsers.entrySet()) {
      output.append(String.format("|%-7s|%15s|", entry.getKey(), entry.getValue()));
      output.append(System.lineSeparator());
    }

    writer.writeOnNewLine(output.toString());
    writer.writeLine("Please select users id [1 or 1, 2 ,3]: ");
    List<String> usersID = new ArrayList<>();
    usersID = Arrays.stream(reader.readLine().split(", ")).collect(Collectors.toList());

    taskService.shareTask(taskId, usersID);
    start();
  }

  private void deleteTask() {
    listAllTasks();
    String taskIdToDelete = null;
    taskIdToDelete = reader.readLine();
    if (taskService.deleteTask(taskIdToDelete)) {
      writer.writeOnNewLine("Task successfully deleted");
    } else {
      writer.writeOnNewLine("Task cannot be deleted");
    }
    start();
  }

  private void editTask() {
    listAllTasks();
    writer.writeLine("Please select Task to edit [ID]: ");
    Long id = null;
    try {
      id = Long.parseLong(reader.readLine());
    } catch (NumberFormatException e) {
      writer.writeOnNewLine("Numbers only");
      writer.writeLine("Please select Task to edit [ID]: ");
      id = Long.parseLong(reader.readLine());
    }
    writer.writeOnNewLine(taskService.getTaskByID(id));
    writer.writeLine("Enter new name: ");
    String name = null;
    name = reader.readLine();
    taskService.editTask(id, name);
    writer.writeLine("Edit another task [Y/N]");
    if (reader.readLine().equalsIgnoreCase("y")) {
      editTask();
    } else {
      start();
    }
  }

  private void createTask() {
    writer.writeOnNewLine("Create new Task");
    writer.writeLine("Task name: ");
    String taskName = "";
    taskName = reader.readLine();
    taskService.createTask(taskName);
    writer.writeLine("Create another task [Y/N]");
    if (reader.readLine().equalsIgnoreCase("y")) {
      createTask();
    } else {
      start();
    }
  }

  private void listAllTasks() {

    // TODO refactor taskService.getAllTasks() to return List of Strings .....

    List<Task> allTask = taskService.getAllTasks();

    writer.writeOnNewLine(TASK_HEADER);
    writer.writeOnNewLine("-".repeat(TASK_HEADER.length()));
    allTask.forEach(System.out::println);
  }

  private int getChoice() {
    int choice = 0;
    try {
      choice = Integer.parseInt(reader.readLine());
      if (choice < 1 || choice > 7) {
        writer.writeOnNewLine(String.format(SELECT_BETWEEN_NUMBERS, 1, 7));
        start();
      }
    } catch (NumberFormatException e) {
      writer.writeOnNewLine(NOT_VALID_INPUT_NUMBERS_ONLY);
      start();
    }

    return choice;
  }
}
