package proj.p1.services;

import java.util.Collection;

import proj.p1.dao.EmployeeDAO;
import proj.p1.dao.EmployeeDAOImpl;
import proj.p1.dao.ManagerDAO;
import proj.p1.dao.ManagerDAOImpl;
import proj.p1.dao.ReimbursementRequestDAO;
import proj.p1.dao.ReimbursementRequestDAOImpl;
import proj.p1.models.Employee;
import proj.p1.models.Manager;
import proj.p1.models.ReimbursementRequest;
import proj.p1.models.RequestStatus;
import proj.p1.models.Token;

public class ManagerServices {

	public Manager managerLoginVerification(String username, String password) {
	
		Manager returnedManager = null;
		
		if(username != null && password != null) {
			ManagerDAO managerDAO = new ManagerDAOImpl();
			Manager tmpManager = managerDAO.getManagerByUser(username);
			if (tmpManager != null) {
				if(tmpManager.getPassword().equals(password)) {
					returnedManager = tmpManager;
				}
			}
		}
		
		return returnedManager;
	}
	
	public Manager verifyToken(Token token) {
		Manager manager = null;
		
		ManagerDAO managerDAO = new ManagerDAOImpl();
		manager = managerDAO.getManagerById(token.getId());
		
		return manager;
	}

	public Collection<ReimbursementRequest> getAllPendingRequests(){
		Collection<ReimbursementRequest> pendingRequests = null;
		
		ReimbursementRequestDAO rRequestDAO = new ReimbursementRequestDAOImpl();
		pendingRequests = rRequestDAO.getAllPendingRequests();
		
		return pendingRequests;
	}
	
	public Collection<ReimbursementRequest> getPendingRequestsByEmployeeId(int employeeId){
		Collection<ReimbursementRequest> pendingRequests = null;
		
		ReimbursementRequestDAO rRequestDAO = new ReimbursementRequestDAOImpl();
		pendingRequests = rRequestDAO.getPendingRequestsByEmployeeId(employeeId);
		
		return pendingRequests;
	}
	
	public Collection<ReimbursementRequest> getResolvedRequestsbyEmployeeId(int employeeId){
		Collection<ReimbursementRequest> pendingRequests = null;
		
		ReimbursementRequestDAO rRequestDAO = new ReimbursementRequestDAOImpl();
		pendingRequests = rRequestDAO.getResolvedRequestsByEmployeeId(employeeId);
		
		return pendingRequests;
	}
	
	public boolean resolveReimbursementRequest(int requestId, RequestStatus requestStatus) {
		boolean haveSuccessfullyResolvedRequest = false;
		
		ReimbursementRequestDAO rRequestDAO = new ReimbursementRequestDAOImpl();
//		RequestStatus requestStatus = RequestStatus.valueOf(strRequestStatus);
		haveSuccessfullyResolvedRequest = rRequestDAO.updateRequestStatus(requestId, requestStatus);
		
		return haveSuccessfullyResolvedRequest;
	}

}
