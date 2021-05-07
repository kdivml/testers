package hu.idomsoft.testers.data;

/**
 *
 * @author TilistyakL
 */
public class System {
  private Integer id;
  private String name;
  private System parent;

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
  
  public System getParent() {
	  return parent;
  }
  
  public void setParent(System parent) {
	  this.parent = parent;
  }
}
