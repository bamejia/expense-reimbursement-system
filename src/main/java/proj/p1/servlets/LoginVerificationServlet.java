package proj.p1.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import proj.p1.main.GlobalVariables;
import proj.p1.models.Employee;
import proj.p1.models.Manager;
import proj.p1.services.EmployeeServices;
import proj.p1.services.ManagerServices;

public class LoginVerificationServlet extends HttpServlet {
	
	private static int count = 0;
	
	private static final long serialVersionUID = 1L;
	private ManagerServices managerServices = new ManagerServices();
	private EmployeeServices employeeServices = new EmployeeServices();
	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//		System.out.println("GET request to Login Verification Servlet");
//		String accountType = request.getParameter("type");
//		System.out.println("requesting type: "+ accountType);
//		
//		response.setStatus(201);
//		try(PrintWriter pw = response.getWriter();){
//			if(accountType != null && accountType.equals("employee")) {
//				pw.write("Hello Fam!");
//			}else if(accountType != null && accountType.equals("manager")) {
//				pw.write("Hello Manager!");
//			}else {
//				pw.write("Who you is?!");
//			}
//		}
//	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ObjectMapper om = new  ObjectMapper();
//		UserAccount credentials = om.readValue(request.getReader().readLine(), UserAccount.class);
		
		JsonNode parent= om.readTree(request.getReader());
		String accountType = parent.path("type").asText();
		String username = parent.path("username").asText();
		String password = parent.path("password").asText();
		
		
		System.out.printf("%d POST request to Login Verification Servlet\n", count++);
		System.out.println("requesting type: "+ accountType);
		
		System.out.println(username + " " + password);
		
		if(accountType != null && username != null && password != null) {
			try(PrintWriter pw = response.getWriter();){
				if(accountType != null && accountType.equals("employee")) {
					Employee employee = employeeServices.employeeLoginVerification(username, password);
					if(employee != null && employee.getId() > 0) {
						response.setStatus(GlobalVariables.RESPONSE_CODE_CREATED);
						pw.write(String.format("%d:employee", employee.getId()));
					}
					else{
						response.setStatus(GlobalVariables.RESPONSE_CODE_UNAUTHORIZED);
					}
				}else if(accountType != null && accountType.equals("manager")) {
					Manager manager = managerServices.managerLoginVerification(username, password);
					if(manager != null && manager.getId() > 0) {
						response.setStatus(GlobalVariables.RESPONSE_CODE_CREATED);
						pw.write(String.format("%d:manager", manager.getId()));
					}
					else{
						response.setStatus(GlobalVariables.RESPONSE_CODE_UNAUTHORIZED);
					}
				}else {
					response.setStatus(GlobalVariables.RESPONSE_CODE_UNAUTHORIZED);
				}
			}
		}
	}
	
}
