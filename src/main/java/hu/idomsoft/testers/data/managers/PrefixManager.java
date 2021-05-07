package hu.idomsoft.testers.data.managers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import hu.idomsoft.testers.data.BaseData;
import hu.idomsoft.testers.data.Prefix;
import hu.idomsoft.testers.data.database.DatabaseConnector;

/**
 *
 * @author TilistyakL
 */
public class PrefixManager {
  public static List<Prefix> getfreePrefixList() {
    String sql = "SELECT id, prefix FROM PREFIX\n"
    			+"WHERE basedata_id IS NULL";
    List<Prefix> prefixList = new ArrayList<>();
    
    Connection connection = DatabaseConnector.getConnection();
    try {
      Statement statement = connection.createStatement();
      ResultSet results  = statement.executeQuery(sql);
      
      while(results.next()) {
        Prefix prefix = new Prefix();
        prefix.setId(results.getInt("id"));
        prefix.setPrefix(results.getString("prefix"));
        
        prefixList.add(prefix);
      }
    } 
    catch (SQLException ex) {
      Logger.getLogger(PrefixManager.class.getName()).log(Level.SEVERE, null, ex);
    }
    finally {
      DatabaseConnector.closeConnection(connection);
    }
    
    return prefixList;
  }
  
  public static List<Prefix> getLinkedPrefixList(BaseData baseData) {
	  String sql = "SELECT id, prefix FROM PREFIX\n"
	    		  +"WHERE basedata_id = " + baseData.getId();
	  List<Prefix> prefixList = new ArrayList<>();
	    
	  Connection connection = DatabaseConnector.getConnection();
	  try {
	    Statement statement = connection.createStatement();
	    ResultSet results  = statement.executeQuery(sql);
	      
	    while(results.next()) {
	      Prefix prefix = new Prefix();
	      prefix.setId(results.getInt("id"));
	      prefix.setPrefix(results.getString("prefix"));
	        
	      prefixList.add(prefix);
	    }
	  } 
	  catch (SQLException ex) {
	    Logger.getLogger(PrefixManager.class.getName()).log(Level.SEVERE, null, ex);
	  }
	  finally {
	    DatabaseConnector.closeConnection(connection);
	  }
	    
	  return prefixList;
  }
  
  public static boolean linkBaseDataToPrefix(List<Prefix> prefixList, BaseData baseData) {
    String list = prefixList.stream()
			  	  .map(prefix -> String.valueOf(prefix.getId()))
			  	  .collect(Collectors.joining(",","(",")"));
	  
    String sql = "UPDATE PREFIX\n"
               + "SET basedata_id = " + ((baseData != null) ? baseData.getId() : "null") + "\n"
               + "WHERE id IN " + list;
    
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
      Logger.getLogger(PrefixManager.class.getName()).log(Level.SEVERE, null, ex);
    }
    finally {
      DatabaseConnector.closeConnection(connection);
    }
    
    return success;
  }
}
