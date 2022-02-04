package todoapp.entities;

import com.google.gson.annotations.SerializedName;
import todoapp.commons.enums.UserRole;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class User extends BaseEntity implements Serializable {

  private String username;
  private String password;

  @SerializedName("first_name")
  private String firstName;

  @SerializedName("last_name")
  private String lastName;

  @SerializedName("user_role")
  private UserRole userRole;

  public User() {}

  public User(String username, String password) {

    this.username = username;
    this.password = password;
  }

  public User(String username, String password, String firstName, String lastName, String role) {

    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.setUserRole(role);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public UserRole getUserRole() {
    return userRole;
  }

  public void setUserRole(String role) {
    this.userRole = UserRole.valueOf(role.toUpperCase(Locale.ROOT));
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("User{");
    sb.append("id=").append(this.getId());
    sb.append(", username='").append(username).append('\'');
    sb.append(", firstName='").append(firstName).append('\'');
    sb.append(", lastName='").append(lastName).append('\'');
    sb.append(", userRole=").append(userRole);
    sb.append('}');
    return sb.toString();
  }
}
