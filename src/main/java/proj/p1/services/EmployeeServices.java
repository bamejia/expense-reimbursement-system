package proj.p1.services;

import java.util.Collection;

import proj.p1.dao.EmployeeDAO;
import proj.p1.dao.EmployeeDAOImpl;
import proj.p1.dao.ReimbursementRequestDAO;
import proj.p1.dao.ReimbursementRequestDAOImpl;
import proj.p1.models.Employee;
import proj.p1.models.ReimbursementRequest;
import proj.p1.models.RequestStatus;
import proj.p1.models.Token;

public class EmployeeServices {
	
	
	public Employee employeeLoginVerification(String username, String password) {
		
		Employee returnedEmployee = null;
		
		if(username != null && password != null) {
			EmployeeDAO employeeDAO = new EmployeeDAOImpl();
			Employee tmpEmployee = employeeDAO.getEmployeeByUser(username);
			if (tmpEmployee != null) {
				if(tmpEmployee.getPassword().equals(password)) {
					returnedEmployee = tmpEmployee;
				}
			}
		}
		
		return returnedEmployee;
	}
	
	public Employee verifyToken(Token token) {
		Employee employee = null;
		
		EmployeeDAO employeeDAO = new EmployeeDAOImpl();
		employee = employeeDAO.getEmployeeById(token.getId());
		
		return employee;
	}
	
	public Collection<ReimbursementRequest> getPendingRequests(int customerId){
		Collection<ReimbursementRequest> pendingRequests = null;
		
		ReimbursementRequestDAO rRequestDAO = new ReimbursementRequestDAOImpl();
		pendingRequests = rRequestDAO.getPendingRequestsByEmployeeId(customerId);
		
		return pendingRequests;
	}
	
	public Collection<ReimbursementRequest> getResolvedRequests(int customerId){
		Collection<ReimbursementRequest> pendingRequests = null;
		
		ReimbursementRequestDAO rRequestDAO = new ReimbursementRequestDAOImpl();
		pendingRequests = rRequestDAO.getResolvedRequestsByEmployeeId(customerId);
		
		return pendingRequests;
	}
	
	public boolean createReimbursementRequest(int employeeID, int amount) {
		boolean haveSuccessfullyCreatedRequest = false;
		
		ReimbursementRequestDAO rRequestDAO = new ReimbursementRequestDAOImpl();
		RequestStatus requestStatus = RequestStatus.PENDING;
		ReimbursementRequest rRequest = new ReimbursementRequest(employeeID, amount, requestStatus);
		haveSuccessfullyCreatedRequest = rRequestDAO.createRequest(rRequest);
		
		return haveSuccessfullyCreatedRequest;
	}
	
}
