package hu.idomsoft.testers.data.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TilistyakL
 */
public class DatabaseConnector {
  
  public static Connection getConnection() {
    try (InputStream file = new FileInputStream("/var/testers/testers.properties")) {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      
      Properties properties = new Properties();
      properties.load(file);
      
      return DriverManager.getConnection(
    		  "jdbc:oracle:thin:@" + properties.getProperty("connection"),
    		  properties.getProperty("username"),
    		  properties.getProperty("password"));
    } 
    catch (ClassNotFoundException | SQLException | IOException ex) {
      Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return null;
  }
  
  public static void closeConnection(Connection connection) {
    try {
      connection.close();
    }
    catch (SQLException ex) {
      Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
