package todoapp.entities;

import com.google.gson.annotations.SerializedName;
import todoapp.commons.enums.TaskStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Task extends BaseEntity implements Serializable {


  private String name;

  @SerializedName("created_date")
  private final LocalDate createdDate = LocalDate.now();

  @SerializedName("creator_id")
  private Long creatorId;

  @SerializedName("task_status")
  private TaskStatus taskStatus;

  @SerializedName("modified_date")
  private LocalDate dateOfModification;

  @SerializedName("modified_by_id")
  private String modifiedById;

  @SerializedName("shared_users_id")
  private List<String> sharedUsersId;

  public Task() {}

  public Task(String name) {
    this.name = name;
    this.sharedUsersId = new ArrayList<>();
    this.setTaskStatus(TaskStatus.IN_PROGRESS);
  }

  public Task(String name, Long creatorId) {
    this.name = name;
    this.creatorId = creatorId;
    this.setTaskStatus(TaskStatus.IN_PROGRESS);
    this.sharedUsersId = new ArrayList<>();
  }


  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getCreatedDate() {
    return this.createdDate;
  }

  public Long getCreatorId() {
    return this.creatorId;
  }

  public void setCreatorId(Long creatorId) {
    this.creatorId = creatorId;
  }

  public LocalDate getDateOfModification() {
    return this.dateOfModification;
  }

  public void setDateOfModification(LocalDate dateOfModification) {
    this.dateOfModification = dateOfModification;
  }

  public String getModifiedById() {
    return this.modifiedById;
  }

  public void setModifiedById(String modifiedById) {
    this.modifiedById = modifiedById;
  }

  public TaskStatus getTaskStatus() {
    return this.taskStatus;
  }

  public void setTaskStatus(TaskStatus taskStatus) {
    this.taskStatus = taskStatus;
  }

  public boolean isShared() {
    return sharedUsersId.size() > 0;
  }

  public List<String> getSharedUsersId() {
    return this.sharedUsersId;
  }

  public void setSharedUsersId(List<String> sharedUsersId) {
    this.sharedUsersId = sharedUsersId;
  }


  @Override
  public String toString() {

    final StringBuilder sb = new StringBuilder();
    sb.append(String.format("|%-5s|", this.getId()));
    sb.append(String.format("%20s|", name));
    sb.append(String.format("%10s|", createdDate));
    sb.append(String.format("%18s|", creatorId));
    sb.append(String.format("%15s|", taskStatus.getName()));
    sb.append(String.format("%11s|", dateOfModification == null ? "N/A" : dateOfModification));
    sb.append(String.format("%11s|", modifiedById == null ? "N/A" : modifiedById));
    sb.append(String.format("%6s|", String.valueOf(isShared()).toUpperCase(Locale.ROOT)));
    sb.append(
        String.format(
            "%20s|", sharedUsersId.size() == 0 ? "N/A" : String.join(", ", sharedUsersId)));
    return sb.toString();
  }
}
