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
import hu.idomsoft.testers.data.database.DatabaseConnector;

/**
 *
 * @author TilistyakL
 */
public class BaseDataManager {
  public static List<BaseData> getBaseDataList() {
    String sql = "SELECT id, name, description FROM BASEDATA";
    List<BaseData> baseDataList = new ArrayList<>();
    
    Connection connection = DatabaseConnector.getConnection();
    try {
      Statement statement = connection.createStatement();
      ResultSet results  = statement.executeQuery(sql);
      
      while(results.next()) {
        BaseData baseData = new BaseData();
        baseData.setId(results.getInt("id"));
        baseData.setName(results.getString("name"));
        baseData.setDescription(results.getString("description"));
        
        baseDataList.add(baseData);
      }
    } 
    catch (SQLException ex) {
      Logger.getLogger(BaseDataManager.class.getName()).log(Level.SEVERE, null, ex);
    }
    finally {
    	DatabaseConnector.closeConnection(connection);
    }
    
    return baseDataList;
  }
  
  public static boolean insertBaseData(BaseData baseData) {
	  String sql = "INSERT INTO BASEDATA (name, description)\n"
			  	 + "VALUES ('" + baseData.getName() + "', '" + baseData.getDescription() + "')";
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
		  Logger.getLogger(BaseDataManager.class.getName()).log(Level.SEVERE, null, ex);
	  }
	  finally {
		  DatabaseConnector.closeConnection(connection);
	  }
	  
	  return success;
  }
  
  public static boolean updateBaseData(BaseData baseData) {
	  String sql = "UPDATE BASEDATA\n"
			  	 + "SET name = '" + baseData.getName() + "',\n"
			  	 + "description = '" + baseData.getDescription() + "'\n"
			  	 + "WHERE id = " + baseData.getId();
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
		  Logger.getLogger(BaseDataManager.class.getName()).log(Level.SEVERE, null, ex);
	  }
	  finally {
		  DatabaseConnector.closeConnection(connection);
	  }
	  
	  return success;
  }
  
  public static boolean deleteBaseData(BaseData baseData) {
	  String sql = "DELETE FROM BASEDATA\n"
			     + "WHERE id = " + baseData.getId(); 
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
		  Logger.getLogger(BaseDataManager.class.getName()).log(Level.SEVERE, null, ex);
	  }
	  finally {
		  DatabaseConnector.closeConnection(connection); 
	  }
	  
	  return success;
  }
}
