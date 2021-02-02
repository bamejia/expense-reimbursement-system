package proj.p1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import proj.p1.models.Manager;
import proj.p1.util.ConnectionUtil;

public class ManagerDAOImpl implements ManagerDAO {
	
	static Logger Log = Logger.getRootLogger();

	@Override
	public Manager getManagerById(int id) {
		String sql = "SELECT * FROM MANAGERS WHERE MANAGER_ID = ?";
		Manager manager = null;
		
		try(Connection connection = ConnectionUtil.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);){
			
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				manager = new Manager();
				manager.setId(rs.getInt("manager_id"));
				manager.setUsername(rs.getString("username"));
				manager.setPassword(rs.getString("password"));
				manager.setFirstName(rs.getString("first_name"));
				manager.setLastName(rs.getString("last_name"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			manager = null;
		}
		
		if(manager != null) {
			Log.info("Successfully retrieved manager with ID: " + id);
		}else {
			Log.warn("Unable to retrieve manager with ID: " + id);
		}
		
		return manager;
	}

	@Override
	public Manager getManagerByUser(String username) {
		String sql = "SELECT * FROM MANAGERS WHERE USERNAME = ?";
		Manager manager = null;
		
		try(Connection connection = ConnectionUtil.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);){
			
			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				manager = new Manager();
				manager.setId(rs.getInt("manager_id"));
				manager.setUsername(rs.getString("username"));
				manager.setPassword(rs.getString("password"));
				manager.setFirstName(rs.getString("first_name"));
				manager.setLastName(rs.getString("last_name"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			manager = null;
		}
		
		if(manager != null) {
			Log.info("Successfully retrieved manager with Username: " + username);
		}else {
			Log.warn("Unable to retrieve manager with Username: " + username);
		}
		
		return manager;
	}

}
