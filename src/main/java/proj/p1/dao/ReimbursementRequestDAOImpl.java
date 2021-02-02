package proj.p1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

import org.apache.log4j.Logger;

import proj.p1.models.ReimbursementRequest;
import proj.p1.models.RequestStatus;
import proj.p1.util.ConnectionUtil;

public class ReimbursementRequestDAOImpl implements ReimbursementRequestDAO {
	
	static Logger Log = Logger.getRootLogger();

	@Override
	public Collection<ReimbursementRequest> getAllPendingRequests() {
		String sql = "SELECT * FROM REIMBURSEMENT_REQUESTS WHERE REQUEST_STATUS = 'PENDING'";
		Collection<ReimbursementRequest> rRequests = null;
		
		try(Connection connection = ConnectionUtil.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);){
			
			ResultSet rs = stmt.executeQuery();
			
			rRequests = new HashSet<ReimbursementRequest>();
			while(rs.next()) {
				ReimbursementRequest rRequest = new ReimbursementRequest();
				rRequest.setId(rs.getInt("request_id"));
				rRequest.setEmployeeId(rs.getInt("employee_id"));
				rRequest.setAmount(rs.getInt("request_amount"));
				RequestStatus status = RequestStatus.valueOf(rs.getString("request_status"));
				rRequest.setStatus(status);
				
				rRequests.add(rRequest);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rRequests = null;
		}
		
		if(rRequests != null) {
			Log.info("Successfully retrieved all pending requests");
		}else {
			Log.warn("Unable to retrieve all pending requests");
		}
		
		return rRequests;
	}
	
	
	@Override
	public Collection<ReimbursementRequest> getPendingRequestsByEmployeeId(int id) {
		String sql = "SELECT RR.REQUEST_ID, RR.EMPLOYEE_ID, RR.REQUEST_AMOUNT, RR.REQUEST_STATUS\r\n"
				+ "FROM EMPLOYEES E JOIN REIMBURSEMENT_REQUESTS RR ON E.EMPLOYEE_ID = RR.EMPLOYEE_ID\r\n"
				+ "WHERE E.EMPLOYEE_ID = ? AND RR.REQUEST_STATUS = 'PENDING'";
		Collection<ReimbursementRequest> rRequests = null;
		
		try(Connection connection = ConnectionUtil.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);){
			
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			rRequests = new HashSet<ReimbursementRequest>();
			while(rs.next()) {
				ReimbursementRequest rRequest = new ReimbursementRequest();
				rRequest.setId(rs.getInt("request_id"));
				rRequest.setEmployeeId(rs.getInt("employee_id"));
				rRequest.setAmount(rs.getInt("request_amount"));
				RequestStatus status = RequestStatus.valueOf(rs.getString("request_status"));
				rRequest.setStatus(status);
				
				rRequests.add(rRequest);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rRequests = null;
		}
		
		if(rRequests != null) {
			Log.info("Successfully retrieved all pending requests by employee with ID: " + id);
		}else {
			Log.warn("Unable to retrieve all pending requests from employee with ID: " + id);
		}
		
		return rRequests;
	}
	
	
	
	
	@Override
	public Collection<ReimbursementRequest> getResolvedRequestsByEmployeeId(int id) {
		String sql = "SELECT RR.REQUEST_ID, RR.EMPLOYEE_ID, RR.REQUEST_AMOUNT, RR.REQUEST_STATUS "
				+ "FROM EMPLOYEES E JOIN REIMBURSEMENT_REQUESTS RR ON E.EMPLOYEE_ID = RR.EMPLOYEE_ID "
				+ "WHERE E.EMPLOYEE_ID = ? AND (RR.REQUEST_STATUS = 'ACCEPTED' OR RR.REQUEST_STATUS = 'DECLINED')";
		Collection<ReimbursementRequest> rRequests = null;
		
		try(Connection connection = ConnectionUtil.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);){
			
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			rRequests = new HashSet<ReimbursementRequest>();
			while(rs.next()) {
				ReimbursementRequest rRequest = new ReimbursementRequest();
				rRequest.setId(rs.getInt("request_id"));
				rRequest.setEmployeeId(rs.getInt("employee_id"));
				rRequest.setAmount(rs.getInt("request_amount"));
				RequestStatus status = RequestStatus.valueOf(rs.getString("request_status"));
				rRequest.setStatus(status);
				
				rRequests.add(rRequest);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rRequests = null;
		}
		
		if(rRequests != null) {
			Log.info("Successfully retrieved all resolved requests by employee with ID: " + id);
		}else {
			Log.warn("Unable to retrieve all resolved requests from employee with ID: " + id);
		}
		
		return rRequests;
	}
	
	
	

	@Override
	public ReimbursementRequest getRequestById(int id) {
		String sql = "SELECT * FROM REIMBURSEMENT_REQUESTS WHERE REQUEST_ID = ?";
		ReimbursementRequest rRequest = null;
		
		try(Connection connection = ConnectionUtil.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);){
			
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				rRequest = new ReimbursementRequest();
				rRequest.setId(rs.getInt("request_id"));
				rRequest.setEmployeeId(rs.getInt("employee_id"));
				rRequest.setAmount(rs.getInt("request_amount"));
				RequestStatus status = RequestStatus.valueOf(rs.getString("request_status"));
				rRequest.setStatus(status);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rRequest = null;
		}
		
		if(rRequest != null) {
			Log.info("Successfully retrieved request with ID: " + id);
		}else {
			Log.warn("Unable to retrieve request with ID: " + id);
		}
		
		return rRequest;
	}

	@Override
	public boolean createRequest(ReimbursementRequest rRequest) {
		String sql = "INSERT INTO REIMBURSEMENT_REQUESTS "
				+ "(EMPLOYEE_ID, REQUEST_AMOUNT, REQUEST_STATUS)\r\n"
				+ "VALUES (?, ?, ?)";
		boolean successfullyCreatedRequest = false;
		
		try(Connection connection = ConnectionUtil.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);){
			
			stmt.setInt(1, rRequest.getEmployeeId());
			stmt.setInt(2, rRequest.getAmount());
			stmt.setString(3, rRequest.getStatus().name());
			
			successfullyCreatedRequest = stmt.executeUpdate() != 0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(successfullyCreatedRequest) {
			Log.info("Successfully created request for employee with ID: " + rRequest.getEmployeeId());
		}else {
			Log.warn("Unable to create requests for employee with ID: " 
					+ rRequest.getEmployeeId());
		}
		
		return successfullyCreatedRequest;
	}

	@Override
	public boolean updateRequestStatus(int requestId, RequestStatus requestStatus) {
		String sql = "UPDATE REIMBURSEMENT_REQUESTS \r\n"
				+ "SET REQUEST_STATUS = ?\r\n"
				+ "WHERE REQUEST_ID = ?";
		boolean successfullyUpdatedRequest = false;
		
		try(Connection connection = ConnectionUtil.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);){
			
			stmt.setString(1, requestStatus.name());
			stmt.setInt(2, requestId);
			
			successfullyUpdatedRequest = stmt.executeUpdate() != 0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(successfullyUpdatedRequest) {
			Log.info("Successfully updated request with ID: " + requestId);
		}else {
			Log.warn("Unable to update request with ID: " 
					+ requestId);
		}
		
		return successfullyUpdatedRequest;
	}

}
