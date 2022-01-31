package todoapp.repositories.interfaces;

import todoapp.entities.Task;

import java.util.List;

public interface TaskRepository {


    List<Task> getAll();

    List<Task> getUserTasks(Long userId);

    Task getTaskById(Long id);

    void updateTask(Task task);

    void deleteTaskById(Long id);

    void shareTask(String taskId, List<String> usersID);


    void addTask(Task task);
}
