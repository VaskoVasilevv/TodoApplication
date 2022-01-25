package todoapp.services;

import todoapp.commons.exceptions.UserExistException;
import todoapp.commons.exceptions.UserNotExistException;
import todoapp.entities.User;
import todoapp.io.FileOperations;
import todoapp.repositories.interfaces.TaskRepository;
import todoapp.repositories.interfaces.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminService {

  private UserRepository userRepository;
  private User user;
  private TaskRepository taskRepository;

  public AdminService() {}

  public AdminService(UserRepository userRepository, TaskRepository taskRepository, User user) {
    this.userRepository = userRepository;
    this.user = user;
    this.taskRepository = taskRepository;
  }

  public boolean createNewUser(
      String username, String password, String firstName, String lastName, String role)
      throws UserExistException {
    User userToPersist = new User(username, password, firstName, lastName, role);
    return userRepository.addUser(userToPersist);
  }

  public Map<Long, String> getListOfUsers() {
    List<User> users = userRepository.getAll();
    Map<Long, String> usersMap = new HashMap<>();
    for (User user : users) {
      usersMap.put(user.getId(), user.getUsername());
    }
    return usersMap;
  }

  public List<String> getUserDetails(Long userID) throws UserNotExistException {
    User user = userRepository.getById(userID);
    List<String> userDetails = new ArrayList<>();
    userDetails.add(user.getUsername());
    userDetails.add(user.getFirstName());
    userDetails.add(user.getLastName());
    userDetails.add(user.getUserRole().toString());

    return userDetails;
  }

  public void editUser(Long userID, List<String> userDetails) {
    User userToEdit = null;
    try {
      userToEdit = userRepository.getById(userID);
    } catch (UserNotExistException ignored) {
    }

    if (userToEdit != null) {
      userToEdit.setUsername(userDetails.get(0));
      userToEdit.setFirstName(userDetails.get(1));
      userToEdit.setLastName(userDetails.get(2));
      userToEdit.setUserRole(userDetails.get(3));
    }
    userRepository.updateUser(user);
  }

  public void deleteUser(Long id) {
    User userToDelete = null;
    try {
      userToDelete = userRepository.getById(id);
    } catch (UserNotExistException ignored) {
    }
    try {
      userRepository.deleteUser(userToDelete);
    } catch (UserNotExistException ignored) {

    }
  }

  public User getUser() {
    return user;
  }

  public void exit() {
    FileOperations.saveToFile(userRepository.getAll(), "users");
    FileOperations.saveToFile(taskRepository.getAll(), "tasks");
    System.exit(0);
  }
}
