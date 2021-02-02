package proj.p1.main;

import java.util.Collection;
import java.util.HashSet;

import proj.p1.dao.EmployeeDAO;
import proj.p1.dao.EmployeeDAOImpl;
import proj.p1.dao.ReimbursementRequestDAO;
import proj.p1.dao.ReimbursementRequestDAOImpl;
import proj.p1.models.Employee;
import proj.p1.models.ReimbursementRequest;
import proj.p1.models.RequestStatus;

public class Main {
	public static void main(String [] args) {
//		String username = "user";
//		String password = "pass";
//		
//		EmployeeServices employeeServices = new EmployeeServices();
//		Employee employee = employeeServices.employeeLoginVerification(username, password);
//		
//		System.out.println(employee);
		
//		String input = "-5423523";
//		
//		boolean isInt = StringParseUtil.isInt(input);
//		System.out.println(isInt);
//		int id = StringParseUtil.parseInt(input);
//		System.out.println(id);
		
		EmployeeDAO employeeDAO = new EmployeeDAOImpl();
		Employee employee = employeeDAO.getEmployeeById(2);
		
		ReimbursementRequestDAO rRequestDAO = new ReimbursementRequestDAOImpl();
		Collection<ReimbursementRequest> rPendingRequests = rRequestDAO
				.getPendingRequestsByEmployeeId(employee.getId());
		Collection<ReimbursementRequest> rResolvedRequests = rRequestDAO
				.getResolvedRequestsByEmployeeId(employee.getId());
		
		Collection<ReimbursementRequest> allRRequests = new HashSet<ReimbursementRequest>();
		allRRequests.addAll(rPendingRequests);
		allRRequests.addAll(rResolvedRequests);
		
//		for(ReimbursementRequest request: rPendingRequests) {
//			System.out.println(request);
//			if(request.getId()==2) {
//				request.setStatus(RequestStatus.DECLINED);
//				rRequestDAO.updateRequestStatus(request);
//			}
//		}
		
		
	}
}
