package todoapp.services;

import todoapp.commons.enums.TaskStatus;
import todoapp.commons.enums.UserRole;
import todoapp.entities.Task;
import todoapp.entities.User;
import todoapp.io.FileOperations;
import todoapp.repositories.interfaces.TaskRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TaskService {
  private final User authorizedUser;
  private final TaskRepository taskRepository;
  private final List<User> users;

  public TaskService(User authorizedUser, TaskRepository taskRepository, List<User> users) {
    this.authorizedUser = authorizedUser;
    this.taskRepository = taskRepository;
    this.users = users;
  }

  public List<Task> getAllTasks() {
    List<Task> taskList;

    if (authorizedUser.getUserRole().equals(UserRole.ADMIN)) {
      taskList = taskRepository.getAll();
    } else {
      taskList = taskRepository.getUserTasks(authorizedUser.getId());
    }
    return taskList.stream()
        .sorted(Comparator.comparingInt(t -> t.getTaskStatus().ordinal()))
        .collect(Collectors.toList());
  }

  public void createTask(String taskName) {
    Task task = new Task(taskName, authorizedUser.getId());
    taskRepository.addTask(task);
  }

  public void editTask(Long id, String name) {
    Task taskById = getAllTasks().stream().filter(t -> t.getId().equals(id)).findFirst().get();
    taskById.setName(name);
    taskById.setDateOfModification(LocalDate.now());
    taskById.setModifiedById(String.valueOf(authorizedUser.getId()));
    taskRepository.updateTask(taskById);
  }

  public boolean deleteTask(String readLine) {
    Long taskId = Long.parseLong(readLine);
    Task taskById = taskRepository.getTaskById(taskId);
    if (taskById.getCreatorId().equals(authorizedUser.getId())
        || authorizedUser.getUserRole().equals(UserRole.ADMIN)) {
      taskRepository.deleteTaskById(taskId);
      return true;
    }
    return false;
  }

  public Map<Long, String> listUsers() {
    Map<Long, String> userMap = new HashMap<>();
    for (User user : users) {
      userMap.put(user.getId(), user.getUsername());
    }
    return userMap;
  }

  public void shareTask(String taskId, List<String> usersID) {
    taskRepository.shareTask(taskId, usersID);
  }

  public String getTaskByID(Long id) {
    return taskRepository.getTaskById(id).toString();
  }

  public void changeStatus(String taskId, String status) {
    Task taskById = taskRepository.getTaskById(Long.valueOf(taskId));
    taskById.setTaskStatus(TaskStatus.valueOf(status));
    taskRepository.updateTask(taskById);
  }

  public void exit() {
    FileOperations.saveToFile(taskRepository.getAll(), "tasks");
    FileOperations.saveToFile(users, "users");
    System.exit(0);
  }
}
