package hu.idomsoft.testers.data;

/**
 *
 * @author TilistyakL
 */
public class User {
  private Integer id;
  private String name;
  private String username;
  private Integer usergroupId;

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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Integer getUsergroupId() {
    return usergroupId;
  }

  public void setUsergroupId(Integer usergroupId) {
    this.usergroupId = usergroupId;
  }
}
