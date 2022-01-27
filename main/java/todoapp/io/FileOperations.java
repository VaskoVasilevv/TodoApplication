package todoapp.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import todoapp.entities.Task;
import todoapp.entities.User;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileOperations {

  private static final String USERS_JSON = "users.json";
  private static final String TASKS_JSON = "tasks.json";

  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  public static List<User> getUsersFromJsonFile() {
    String strFromFile = null;
    if (!FileUtils.getFile(USERS_JSON).exists()) {
      try {
        FileUtils.touch(new File(USERS_JSON));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    File file = FileUtils.getFile(USERS_JSON);
    try {
      strFromFile = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (Objects.requireNonNull(strFromFile).isEmpty()) {
      return new LinkedList<>();
    }
    return Arrays.stream(GSON.fromJson(strFromFile, User[].class)).collect(Collectors.toList());
  }

  public static <E> void saveToFile(List<E> list, String name) {
    File file;
    if (name.equals("users")) {
      file = FileUtils.getFile(USERS_JSON);
    } else {
      file = FileUtils.getFile(TASKS_JSON);
    }
    String toJson = GSON.toJson(list);
    try {
      FileUtils.writeStringToFile(file, toJson, StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static List<Task> getTasks() {
    String strFromFile = null;
    if (!FileUtils.getFile(TASKS_JSON).exists()) {
      try {
        FileUtils.touch(new File(TASKS_JSON));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    File file = FileUtils.getFile(TASKS_JSON);
    try {
      strFromFile = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (Objects.requireNonNull(strFromFile).isEmpty()) {
      return new LinkedList<>();
    }
    return Arrays.stream(GSON.fromJson(strFromFile, Task[].class)).collect(Collectors.toList());
  }
}
