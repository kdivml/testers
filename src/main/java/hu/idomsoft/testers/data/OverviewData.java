package hu.idomsoft.testers.data;

public class OverviewData extends SubSystem{
	String systemName;
	String testerName;
	String baseDataName;
	
	public String getSystemName() {
		return systemName;
	}
	
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
	public String getTesterName() {
		return testerName;
	}
	
	public void setTesterName(String testerName) {
		this.testerName = testerName;
	}
	
	public String getBaseDataName() {
		return baseDataName;
	}
	
	public void setBaseDataName(String baseDataName) {
		this.baseDataName = baseDataName;
	}
}
