package todoapp.repositories.interfaces;

import todoapp.commons.exceptions.UserExistException;
import todoapp.commons.exceptions.UserNotExistException;
import todoapp.entities.User;

import java.util.List;

public interface UserRepository {

  boolean addUser(User user) throws UserExistException;

  void deleteUser(User user) throws UserNotExistException;

  void updateUser(User user);

  List<User> getAll();

  User getById(Long id) throws UserNotExistException;

  User getByUsername(String username) throws UserNotExistException;
}
