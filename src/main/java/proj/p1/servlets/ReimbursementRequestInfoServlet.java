package proj.p1.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import proj.p1.dao.EmployeeDAO;
import proj.p1.dao.EmployeeDAOImpl;
import proj.p1.dao.ManagerDAO;
import proj.p1.dao.ManagerDAOImpl;
import proj.p1.dao.ReimbursementRequestDAO;
import proj.p1.dao.ReimbursementRequestDAOImpl;
import proj.p1.main.GlobalVariables;
import proj.p1.models.AccountType;
import proj.p1.models.Employee;
import proj.p1.models.Manager;
import proj.p1.models.ReimbursementRequest;
import proj.p1.models.ReimbursementToken;
import proj.p1.models.ReimbursementTokenType;
import proj.p1.models.RequestStatus;
import proj.p1.models.Token;
import proj.p1.models.UserAccount;
import proj.p1.services.EmployeeServices;
import proj.p1.services.ManagerServices;
import proj.p1.util.HttpRequestParseUtil;

public class ReimbursementRequestInfoServlet extends HttpServlet{

	private static int count = 0;
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String strToken = request.getParameter("token");
		Token token = HttpRequestParseUtil.parseToken(strToken);
		String strReimbursementToken = request.getParameter("reimbursementToken");
		ReimbursementToken rToken = HttpRequestParseUtil.parseReimbursementToken(strReimbursementToken);
		
		UserAccount user = verifyUser(token);
		
//		System.out.printf("%d GET request to ReimbursementRequestInfoServlet\n", count++);
//		System.out.println(token);
//		System.out.println(rToken + "\n");
		
		boolean requestIsValid = false;
		if(user != null && rToken != null) {
			if(token.getAccountType() == AccountType.EMPLOYEE) {
				EmployeeDAO employeeDAO = new EmployeeDAOImpl();
				Employee employee = (Employee) user;
				
				ReimbursementRequestDAO rRequestDAO = new ReimbursementRequestDAOImpl();
				if(rToken.getType() == ReimbursementTokenType.PENDING_EMPLOYEE_REQUESTS) {
					requestIsValid = true;
					Collection<ReimbursementRequest> rRequests = rRequestDAO
							.getPendingRequestsByEmployeeId(rToken.getId());
					try(PrintWriter pw = response.getWriter();){
						ObjectMapper om = new ObjectMapper();
						String rRequestsJSON = om.writeValueAsString(rRequests);
						pw.write(rRequestsJSON);
						response.setStatus(GlobalVariables.RESPONSE_CODE_OK);
					}
				}else if(rToken.getType() == ReimbursementTokenType.RESOLVED_EMPLOYEE_REQUESTS) {
					requestIsValid = true;
					Collection<ReimbursementRequest> rRequests = rRequestDAO
							.getResolvedRequestsByEmployeeId(rToken.getId());
					try(PrintWriter pw = response.getWriter();){
						ObjectMapper om = new ObjectMapper();
						String rRequestsJSON = om.writeValueAsString(rRequests);
						pw.write(rRequestsJSON);
						response.setStatus(GlobalVariables.RESPONSE_CODE_OK);
					}
				}
			}else if(token.getAccountType() == AccountType.MANAGER) {
				ManagerServices ms = new ManagerServices();
//				Manager manager = (Manager) user;
//					requestIsValid = true;
//					try(PrintWriter pw = response.getWriter();){
//						manager.setPassword(null);
//						ObjectMapper om = new ObjectMapper();
//						String managerJSON = om.writeValueAsString(manager);
//						pw.write(managerJSON);
//						response.setStatus(GlobalVariables.RESPONSE_CODE_OK);
//					}
				if(rToken.getType() == ReimbursementTokenType.ALL_PENDING_REQUESTS) {
					Collection<ReimbursementRequest> rRequests = ms.getAllPendingRequests();
					
					if(rRequests != null) {
						requestIsValid = true;
						
						try(PrintWriter pw = response.getWriter();){
							ObjectMapper omResponse = new ObjectMapper();
							String rRequestsJSON = omResponse.writeValueAsString(rRequests);
							pw.write(rRequestsJSON);
							response.setStatus(GlobalVariables.RESPONSE_CODE_OK);
						}	
					}
				}else if(rToken.getType() == ReimbursementTokenType.PENDING_EMPLOYEE_REQUESTS) {
					Collection<ReimbursementRequest> rRequests = ms
							.getPendingRequestsByEmployeeId(rToken.getId());
					
					if(rRequests != null) {
						requestIsValid = true;
						
						try(PrintWriter pw = response.getWriter();){
							ObjectMapper omResponse = new ObjectMapper();
							String rRequestsJSON = omResponse.writeValueAsString(rRequests);
							pw.write(rRequestsJSON);
							response.setStatus(GlobalVariables.RESPONSE_CODE_OK);
						}
					}
				}else if(rToken.getType() == ReimbursementTokenType.RESOLVED_EMPLOYEE_REQUESTS) {
					Collection<ReimbursementRequest> rRequests = ms
							.getResolvedRequestsbyEmployeeId(rToken.getId());
					
					if(rRequests != null) {
						requestIsValid = true;
						
						try(PrintWriter pw = response.getWriter();){
							ObjectMapper omResponse = new ObjectMapper();
							String rRequestsJSON = omResponse.writeValueAsString(rRequests);
							pw.write(rRequestsJSON);
							response.setStatus(GlobalVariables.RESPONSE_CODE_OK);
						}
					}
				}
			}
		}
		
		if(!requestIsValid) {  // If user is not valid, sends an "unauthorized entry" code
			response.setStatus(GlobalVariables.RESPONSE_CODE_UNAUTHORIZED);
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ObjectMapper omRequest = new  ObjectMapper();
		
		JsonNode parent= omRequest.readTree(request.getReader());
		String strToken = parent.path("token").asText();
		String strReimbursementToken = parent.path("reimbursementToken").asText();

		Token token = HttpRequestParseUtil.parseToken(strToken);
		ReimbursementToken rToken = HttpRequestParseUtil.parseReimbursementToken(strReimbursementToken);
		
		UserAccount user = verifyUser(token);
		
//		System.out.printf("%d POST request to ReimbursementRequestInfoServlet\n", count++);
//		System.out.println(token);
//		System.out.println(strReimbursementToken);
//		System.out.println(rToken + "\n");
		
		boolean requestIsValid = false;
		if(user != null && rToken != null) {
			if(token.getAccountType() == AccountType.EMPLOYEE) {
				// Employee cannot update their reimbursement request
			}else if(token.getAccountType() == AccountType.MANAGER) {
				ManagerDAO managerDAO = new ManagerDAOImpl();
				Manager manager = (Manager) user;
				
				ReimbursementRequestDAO rRequestDAO = new ReimbursementRequestDAOImpl();
				if(rToken.getType() == ReimbursementTokenType.ACCEPT_REQUEST) {
					
//					ReimbursementRequest rRequest = rRequestDAO.getRequestById(rToken.getId());
					ManagerServices ms = new ManagerServices();
					requestIsValid = ms.resolveReimbursementRequest(
							rToken.getId(), RequestStatus.ACCEPTED);
					if(requestIsValid) {
						response.setStatus(GlobalVariables.RESPONSE_CODE_CREATED);
					}
				}if(rToken.getType() == ReimbursementTokenType.DECLINE_REQUEST) {
					ManagerServices ms = new ManagerServices();
					requestIsValid = ms.resolveReimbursementRequest(
							rToken.getId(), RequestStatus.DECLINED);
					if(requestIsValid) {
						response.setStatus(GlobalVariables.RESPONSE_CODE_CREATED);
					}
				}
			}
		}
		
		if(!requestIsValid) {  // If user is not valid, sends an "unauthorized entry" code
			response.setStatus(GlobalVariables.RESPONSE_CODE_UNAUTHORIZED);
		}

}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		ObjectMapper omRequest = new  ObjectMapper();
		
		JsonNode parent= omRequest.readTree(request.getReader());
		String strToken = parent.path("token").asText();
		String strReimbursementToken = parent.path("reimbursementToken").asText();

		Token token = HttpRequestParseUtil.parseToken(strToken);
		ReimbursementToken rToken = HttpRequestParseUtil.parseReimbursementToken(strReimbursementToken);
		
		UserAccount user = verifyUser(token);
		
//		System.out.printf("%d PUT request to ReimbursementRequestInfoServlet\n", count++);
//		System.out.println(token);
//		System.out.println(strReimbursementToken);
//		System.out.println(rToken + "\n");
		
		boolean requestIsValid = false;
		if(user != null && rToken != null) {
			if(token.getAccountType() == AccountType.EMPLOYEE) {
				EmployeeDAO employeeDAO = new EmployeeDAOImpl();
				Employee employee = (Employee) user;
					
				EmployeeServices es = new EmployeeServices();
				if(rToken.getType() == ReimbursementTokenType.CREATE_REQUEST) {
					requestIsValid = es.createReimbursementRequest(
							employee.getId(), rToken.getAmount());
					if(requestIsValid){
						response.setStatus(GlobalVariables.RESPONSE_CODE_CREATED);
					}
				
				}
			}else if(token.getAccountType() == AccountType.MANAGER) {
				// Managers do not create reimbursement requests
			}
		}
		
		if(!requestIsValid) {  // If user is not valid, sends an "unauthorized entry" code
			response.setStatus(GlobalVariables.RESPONSE_CODE_UNAUTHORIZED);
		}
	}
	
	private UserAccount verifyUser(Token token) {
		UserAccount user = null;
		
		if(token != null) {
			if(token.getAccountType() == AccountType.EMPLOYEE) {
				EmployeeServices es = new EmployeeServices();
				user = es.verifyToken(token);
			}else if(token.getAccountType() == AccountType.MANAGER) {
				ManagerServices ms = new ManagerServices();
				user = ms.verifyToken(token);
			}
		}
		
		return user;
	}
	
}
