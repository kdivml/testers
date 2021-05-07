package hu.idomsoft.testers.data.managers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import hu.idomsoft.testers.data.System;

import hu.idomsoft.testers.data.database.DatabaseConnector;

/**
 *
 * @author TilistyakL
 */
public class SystemManager {
  public static List<System> getSystemList() {
    String sql = "SELECT id, name FROM SYSTEM";
    List<System> systemList = new ArrayList<>();
    
    Connection connection = DatabaseConnector.getConnection();
    try {
      Statement statement = connection.createStatement();
      ResultSet results  = statement.executeQuery(sql);
      
      while(results.next()) {
        System system = new System();
        system.setId(results.getInt("id"));
        system.setName(results.getString("name"));
        
        systemList.add(system);
      }
    } 
    catch (SQLException ex) {
      Logger.getLogger(SystemManager.class.getName()).log(Level.SEVERE, null, ex);
    }
    finally {
    	DatabaseConnector.closeConnection(connection);
    }
 
    return systemList; 
  }
}
