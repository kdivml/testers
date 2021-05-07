package hu.idomsoft.testers.data.managers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import hu.idomsoft.testers.data.BaseData;
import hu.idomsoft.testers.data.SubSystem;
import hu.idomsoft.testers.data.database.DatabaseConnector;

/**
 *
 * @author TilistyakL
 */
public class SubSystemManager {
  public static List<SubSystem> getSubSystemList() {
    String sql = "SELECT id, name, system_id FROM SUBSYSTEM";
    List<SubSystem> subSystemList = new ArrayList<>();
    
    Connection connection = DatabaseConnector.getConnection();
    try {
      Statement statement = connection.createStatement();
      ResultSet results  = statement.executeQuery(sql);
      
      while(results.next()) {
        SubSystem subSystem = new SubSystem();
        subSystem.setId(results.getInt("id"));
        subSystem.setName(results.getString("name"));
        subSystem.setSystemId(results.getInt("system_id"));
        
        subSystemList.add(subSystem);
      }
    } 
    catch (SQLException ex) {
      Logger.getLogger(SubSystemManager.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    DatabaseConnector.closeConnection(connection);
    return subSystemList;
  }
  
  public static SubSystem getSubSystemBindings(Integer id) {
	  String sql = "SELECT tester_id, basedata_id FROM SUBSYSTEM\n"
	    		   + "WHERE id = " + id;
	  SubSystem subSystem = new SubSystem();
	    
	  Connection connection = DatabaseConnector.getConnection();
	  try {
	    Statement statement = connection.createStatement();
	    ResultSet results  = statement.executeQuery(sql);
	      
	    while(results.next()) {
	      subSystem.setId(id);
	      subSystem.setTesterId(results.getInt("tester_id"));
	      if(subSystem.getTesterId() == 0 && results.wasNull()) {
	    	  subSystem.setTesterId(null);
	      }
	      subSystem.setBaseDataId(results.getInt("basedata_id"));
	      if(subSystem.getBaseDataId() == 0 && results.wasNull()) {
	        subSystem.setBaseDataId(null);
	      }
	    }
	  } 
	  catch (SQLException ex) {
	    Logger.getLogger(SubSystemManager.class.getName()).log(Level.SEVERE, null, ex);
	  }
	  finally {
	    DatabaseConnector.closeConnection(connection);
	  }
	    
	  return subSystem;
	}
  
  public static boolean updateSubSystem(SubSystem subSystem) {
    String sql = "UPDATE SUBSYSTEM\n"
               + "SET tester_id = " + subSystem.getTesterId() + ",\n"
               + "basedata_id = " + subSystem.getBaseDataId() + "\n"
               + "WHERE id = " + subSystem.getId();
    boolean success = false;
    
    Connection connection = DatabaseConnector.getConnection();
    try {
      Statement statement = connection.createStatement();
      int affectedRows = statement.executeUpdate(sql);
      
      if(affectedRows > 0) {
        success = true;
      } 
    }
    catch(SQLException ex) {
      Logger.getLogger(SubSystemManager.class.getName()).log(Level.SEVERE, null, ex);
    }
    finally {
    	DatabaseConnector.closeConnection(connection);
    }
    
    return success;
  }
  
  public static List<SubSystem> getBindedSubSystemsForBaseData(BaseData baseData) {
	  String sql = "SELECT id, name, system_id FROM SUBSYSTEM\n"
	    		 + "WHERE basedata_id = " + baseData.getId();
	  List<SubSystem> subSystemList = new ArrayList<>();
	    
	  Connection connection = DatabaseConnector.getConnection();
	  try {
	    Statement statement = connection.createStatement();
	    ResultSet results  = statement.executeQuery(sql);
	      
	    while(results.next()) {
	      SubSystem subSystem = new SubSystem();
	      subSystem.setId(results.getInt("id"));
	      subSystem.setName(results.getString("name"));
	      subSystem.setSystemId(results.getInt("system_id"));
	        
	      subSystemList.add(subSystem);
	    }
	  } 
	  catch (SQLException ex) {
	    Logger.getLogger(SubSystemManager.class.getName()).log(Level.SEVERE, null, ex);
	  }
	    
	  DatabaseConnector.closeConnection(connection);
	  return subSystemList;
	}
}
