package hu.idomsoft.testers.data.managers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import hu.idomsoft.testers.data.Tester;
import hu.idomsoft.testers.data.database.DatabaseConnector;

/**
 *
 * @author TilistyakL
 */
public class TesterManager {
  public static List<Tester> getTesterList() {
    String sql = "SELECT id, name FROM TESTER";
    List<Tester> testerList = new ArrayList<>();
    
    Connection connection = DatabaseConnector.getConnection();
    try {
      Statement statement = connection.createStatement();
      ResultSet results  = statement.executeQuery(sql);
      
      while(results.next()) {
        Tester tester = new Tester();
        tester.setId(results.getInt("id"));
        tester.setName(results.getString("name"));
        
        testerList.add(tester);
      }
    } 
    catch (SQLException ex) {
      Logger.getLogger(TesterManager.class.getName()).log(Level.SEVERE, null, ex);
    }
    finally {
      DatabaseConnector.closeConnection(connection);
    }
    
    return testerList;
  }
}
