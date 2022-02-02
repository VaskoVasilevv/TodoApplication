package todoapp.commons.enums;

public enum TaskStatus {
  IN_PROGRESS("In Progress"),
  COMPLETED("Completed");

  String name;

  TaskStatus(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
