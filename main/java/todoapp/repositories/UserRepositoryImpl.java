package todoapp.repositories;

import at.favre.lib.crypto.bcrypt.BCrypt;
import todoapp.commons.exceptions.UserExistException;
import todoapp.commons.exceptions.UserNotExistException;
import todoapp.entities.User;
import todoapp.repositories.interfaces.UserRepository;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {

  private List<User> users;

  public UserRepositoryImpl(List<User> userList) {
    users = userList;
    setSuperUser();
  }

  @Override
  public boolean addUser(User user) throws UserExistException {
    if (isExisting(user.getUsername())) {
      throw new UserExistException(
          String.format("User with %s username already exist", user.getUsername()));
    }
    Long id = getId();
    user.setId(id);
    users.add(user);

    return true;
  }

  @Override
  public void deleteUser(User user) throws UserNotExistException {
    if (isExisting(user.getUsername())) {
      users.remove(user);
    } else {
      throw new UserNotExistException(
          String.format("User with Username: %s - not exist ", user.getUsername()));
    }
  }

  @Override
  public void updateUser(User user) {
    if (isExisting(user.getUsername())) {
      Integer index = null;
      for (int i = 0; i < users.size(); i++) {
        if (users.get(i).getId().equals(user.getId())) {
          index = i;
          break;
        }
      }

      users.set(index, user);
    }
  }

  @Override
  public List<User> getAll() {

    return users;
  }

  @Override
  public User getById(Long id) throws UserNotExistException {
    if (isExisting(id)) {
      return users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    } else {
      throw new UserNotExistException(String.format("User with ID %d - not exist ", id));
    }
  }

  @Override
  public User getByUsername(String username) {
    return users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
  }

  // == private methods ==

  private boolean isExisting(Long id) {
    return users.stream().anyMatch(u -> u.getId().equals(id));
  }

  private boolean isExisting(String username) {
    return users.stream().anyMatch(u -> u.getUsername().equals(username));
  }

  private void setSuperUser() {
    if (users.isEmpty()) {
      User superUser =
          new User(
              "admin",
              String.valueOf(BCrypt.withDefaults().hashToChar(6, "adminpass".toCharArray())));
      superUser.setUserRole("admin");
      users.add(superUser);
    }
  }

  private Long getId() {
    return (long) users.get(users.size() - 1).getId() + 1;
  }
}
