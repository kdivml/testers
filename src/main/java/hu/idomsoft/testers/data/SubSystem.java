package hu.idomsoft.testers.data;

/**
 *
 * @author TilistyakL
 */
public class SubSystem extends System{
  private Integer systemId;
  private Integer testerId;
  private Integer baseDataId;

  public Integer getSystemId() {
    return systemId;
  }

  public void setSystemId(Integer systemId) {
    this.systemId = systemId;
  }

  public Integer getTesterId() {
    return testerId;
  }

  public void setTesterId(Integer testerId) {
    this.testerId = testerId;
  }

  public Integer getBaseDataId() {
    return baseDataId;
  }

  public void setBaseDataId(Integer baseDataId) {
    this.baseDataId = baseDataId;
  }
}
