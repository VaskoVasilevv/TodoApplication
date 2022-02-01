package todoapp.repositories;

import todoapp.entities.Task;
import todoapp.repositories.interfaces.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class TaskRepositoryImpl implements TaskRepository {


  private List<Task> tasks;

  public TaskRepositoryImpl(List<Task> taskList) {
    this.tasks = taskList;
  }

  @Override
  public List<Task> getAll() {
    return tasks;
  }

  @Override
  public List<Task> getUserTasks(Long userId) {
    List<Task> taskList = getAll();
    List<Task> userTasks = new ArrayList<>();

    for (Task task : taskList) {
      if (task.getCreatorId().equals(userId)) {
        userTasks.add(task);
      } else if (task.getSharedUsersId().contains(String.valueOf(userId))) {
        userTasks.add(task);
      }
    }

    return userTasks;
  }

  @Override
  public Task getTaskById(Long id) {
    return tasks.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
  }

  @Override
  public void updateTask(Task taskToUpdate) {
    int index = 0;
    index =
        IntStream.range(0, tasks.size())
            .filter(i -> tasks.get(i).getId().equals(taskToUpdate.getId()))
            .findFirst()
            .orElse(index);
    tasks.set(index, taskToUpdate);
  }

  @Override
  public void deleteTaskById(Long id) {
    int index = 0;
    index =
        IntStream.range(0, tasks.size())
            .filter(i -> tasks.get(i).getId().equals(id))
            .findFirst()
            .orElse(index);
    tasks.remove(index);
  }

  @Override
  public void shareTask(String taskId, List<String> usersID) {
    Task task = getTaskById(Long.valueOf(taskId));
    task.setSharedUsersId(usersID);
    updateTask(task);
  }

  @Override
  public void addTask(Task task) {
    Long id = getId();
    task.setId(id);
    tasks.add(task);
  }

  private Long getId() {
    return (long) tasks.size() == 0 ? 0 : tasks.get(tasks.size() - 1).getId() + 1;
  }
}
