package todoapp.services;

import todoapp.commons.exceptions.PasswordNotMatchException;
import todoapp.commons.exceptions.UserNotExistException;
import todoapp.io.FileOperations;
import todoapp.services.auth.Auth;
import todoapp.entities.User;
import todoapp.repositories.TaskRepositoryImpl;
import todoapp.repositories.interfaces.TaskRepository;
import todoapp.repositories.interfaces.UserRepository;
import todoapp.repositories.UserRepositoryImpl;

import java.util.List;
import java.util.Objects;

public class HomeService {

  private final UserRepository userRepository;
  private final TaskRepository taskRepository;

  public HomeService() {
    this.userRepository = new UserRepositoryImpl(FileOperations.getUsersFromJsonFile());
    this.taskRepository = new TaskRepositoryImpl(FileOperations.getTasks());
  }

  public User authorize(String[] userDetails)
      throws PasswordNotMatchException, UserNotExistException {
    User user;
    try {
      user = userRepository.getByUsername(userDetails[0]);
    } catch (UserNotExistException e) {
      throw new UserNotExistException(String.format("User with Username: %s - not exist ",userDetails[0]));
    }

    if (Auth.isAuthorized(Objects.requireNonNull(user).getPassword(), userDetails[1])) {
      return user;
    } else {
      throw new PasswordNotMatchException();
    }
  }

  public AdminService getAdminService(User user) {
    return new AdminService(userRepository,taskRepository, user);
  }

  public TaskService getTaskService(User user) {
    return new TaskService(user, taskRepository, userRepository.getAll());
  }
}
