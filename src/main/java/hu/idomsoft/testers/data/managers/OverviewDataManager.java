package hu.idomsoft.testers.data.managers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import hu.idomsoft.testers.data.OverviewData;
import hu.idomsoft.testers.data.database.DatabaseConnector;

public class OverviewDataManager {
	public static List<OverviewData> getOverviewDataList() {
		String sql = 
				"SELECT " 
		      + "SUBSYSTEM.ID AS ID, "
			  + "SYSTEM.NAME AS SYSTEM_NAME, "
		      + "SUBSYSTEM.NAME AS NAME, "
			  + "TESTER.NAME AS TESTER, "
		      + "BASEDATA.NAME AS BASEDATA " 
			  + "FROM SUBSYSTEM\n"
			  + "LEFT JOIN SYSTEM ON SYSTEM.ID = SUBSYSTEM.SYSTEM_ID\n"
			  + "LEFT JOIN TESTER ON TESTER.ID = SUBSYSTEM.TESTER_ID\n"
			  + "LEFT JOIN BASEDATA ON BASEDATA.ID = SUBSYSTEM.BASEDATA_ID";
		List<OverviewData> dataList = new ArrayList<>();
		
		Connection connection = DatabaseConnector.getConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet results = statement.executeQuery(sql);
			
			while(results.next()) {
				OverviewData data = new OverviewData();
				data.setId(results.getInt("ID"));
				data.setSystemName(results.getString("SYSTEM_NAME"));
				data.setName(results.getString("NAME"));
				data.setTesterName(results.getString("TESTER"));
				data.setBaseDataName(results.getString("BASEDATA"));
				
				dataList.add(data);
			}
		}
		catch (SQLException ex) {
			Logger.getLogger(OverviewDataManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		DatabaseConnector.closeConnection(connection);
		return dataList;
	}
}
