package hu.idomsoft.testers.data;

import java.io.File;

/**
 *
 * @author TilistyakL
 */
public class BaseData {
  private Integer id;
  private String name;
  private String description;
  private File binary;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public File getBinary() {
    return binary;
  }

  public void setBinary(File binary) {
    this.binary = binary;
  }
}
