package todoapp.entities;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BaseEntity {

  private static AtomicInteger generateId = new AtomicInteger(0);

  private Long id;

  public BaseEntity() {
    setId((long) generateId.getAndIncrement());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BaseEntity)) return false;

    BaseEntity that = (BaseEntity) o;

    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}
