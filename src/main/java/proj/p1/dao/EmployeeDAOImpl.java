package proj.p1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

import org.apache.log4j.Logger;

import proj.p1.models.Employee;
import proj.p1.util.ConnectionUtil;

public class EmployeeDAOImpl implements EmployeeDAO {

	static Logger Log = Logger.getRootLogger();
	
	@Override
	public Collection<Employee> getEmployeesWithPendingRequests() {
		String sql = "SELECT DISTINCT * FROM (\r\n"
				+ "SELECT E.EMPLOYEE_ID, E.USERNAME, E.PASSWORD, E.FIRST_NAME, E.LAST_NAME "
				+ "FROM EMPLOYEES E JOIN REIMBURSEMENT_REQUESTS RR ON E.EMPLOYEE_ID = RR.EMPLOYEE_ID "
				+ "WHERE RR.REQUEST_STATUS = 'PENDING'\r\n"
				+ ")";
		
		Collection<Employee> employees = null;
		
		try(Connection connection = ConnectionUtil.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);){
			
			ResultSet rs = stmt.executeQuery();
			
			employees = new HashSet<Employee>();
			while(rs.next()) {
				Employee employee = new Employee();
				employee.setId(rs.getInt("employee_id"));
				employee.setUsername(rs.getString("username"));
				employee.setPassword(rs.getString("password"));
				employee.setFirstName(rs.getString("first_name"));
				employee.setLastName(rs.getString("last_name"));
				
				employees.add(employee);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			employees = null;
		}
		
		if(employees != null) {
			Log.info("Successfully retrieved all pending requests");
		}else {
			Log.warn("Unable to retrieve all pending requests");
		}
		
		return employees;
	}
	
	
/*--------------------------------------------------------------------------------------------------*/

	
	@Override
	public Employee getEmployeeById(int id) {
		String sql = "SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID = ?";
		Employee employee = null;
		
		try(Connection connection = ConnectionUtil.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);){
			
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				employee = new Employee();
				employee.setId(rs.getInt("employee_id"));
				employee.setUsername(rs.getString("username"));
				employee.setPassword(rs.getString("password"));
				employee.setFirstName(rs.getString("first_name"));
				employee.setLastName(rs.getString("last_name"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			employee = null;
		}
		
		if(employee != null) {
			Log.info("Successfully retrieved employee with ID: " + id);
		}else {
			Log.warn("Unable to retrieve employee with ID: " + id);
		}
		
		return employee;
	}

	
/*--------------------------------------------------------------------------------------------------*/
	
	
	@Override
	public Employee getEmployeeByUser(String username) {
		String sql = "SELECT * FROM EMPLOYEES WHERE USERNAME = ?";
		Employee employee = null;
		
		try(Connection connection = ConnectionUtil.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);){
			
			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				employee = new Employee();
				employee.setId(rs.getInt("employee_id"));
				employee.setUsername(rs.getString("username"));
				employee.setPassword(rs.getString("password"));
				employee.setFirstName(rs.getString("first_name"));
				employee.setLastName(rs.getString("last_name"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			employee = null;
		}
		
		if(employee != null) {
			Log.info("Successfully retrieved employee with Username: " + username);
		}else {
			Log.warn("Unable to retrieve employee with Username: " + username);
		}
		
		return employee;
	}

}
