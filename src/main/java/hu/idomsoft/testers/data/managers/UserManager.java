package hu.idomsoft.testers.data.managers;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import hu.idomsoft.testers.data.User;
import hu.idomsoft.testers.data.database.DatabaseConnector;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author TilistyakL
 */
public class UserManager {
  public static User getUser(String name, String username) {
    String sql = "SELECT id, name, usergroup_id FROM USERS WHERE username = '" + username + "'";
    User user = new User();
    
    Connection connection = DatabaseConnector.getConnection();
    try {
      Statement statement = connection.createStatement();
      ResultSet results  = statement.executeQuery(sql);
      
      if(results.next()) {
        user.setId(results.getInt("id"));
        user.setName(results.getString("name"));
        user.setUsername(username);
        user.setUsergroupId(results.getInt("usergroup_id"));
      }
      else {
        user = createUser(connection, name, username);
      }
    } 
    catch (SQLException ex) {
      Logger.getLogger(SystemManager.class.getName()).log(Level.SEVERE, null, ex);
    }
    finally {
      DatabaseConnector.closeConnection(connection);
    }
    
    return user;
  }
  
  private static User createUser(Connection connection, String name, String username) throws SQLException {
    String sql = "BEGIN INSERT INTO USERS "
    		   + "(name, username) VALUES ('" + name + "', '" + username + "') "
    		   + "RETURNING id INTO ?; END;";
    CallableStatement cs = connection.prepareCall(sql);
    
    cs.registerOutParameter(1, OracleTypes.NUMBER);
    cs.execute();
    User user = new User();
    user.setId(cs.getInt(1));
    user.setName(name);
    user.setUsername(username);
    
    return user;
  }
}
