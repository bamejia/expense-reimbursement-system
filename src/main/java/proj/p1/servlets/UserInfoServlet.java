package proj.p1.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import proj.p1.dao.EmployeeDAO;
import proj.p1.dao.EmployeeDAOImpl;
import proj.p1.dao.ManagerDAO;
import proj.p1.dao.ManagerDAOImpl;
import proj.p1.main.GlobalVariables;
import proj.p1.models.AccountType;
import proj.p1.models.Employee;
import proj.p1.models.Manager;
import proj.p1.models.Token;
import proj.p1.models.UserAccount;
import proj.p1.services.EmployeeServices;
import proj.p1.services.ManagerServices;
import proj.p1.util.HttpRequestParseUtil;

public class UserInfoServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String tokenStr = request.getParameter("token");
		Token token = HttpRequestParseUtil.parseToken(tokenStr);
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
		
		if(user != null) {
			if(token.getAccountType() == AccountType.EMPLOYEE) {
				Employee employee = (Employee) user;
				try(PrintWriter pw = response.getWriter();){
					employee.setPassword(null);
					ObjectMapper om = new ObjectMapper();
					String employeeJSON = om.writeValueAsString(employee);
					pw.write(employeeJSON);
					response.setStatus(GlobalVariables.RESPONSE_CODE_CREATED);
				}
			}else if(token.getAccountType() == AccountType.MANAGER) {
				Manager manager = (Manager) user;
				try(PrintWriter pw = response.getWriter();){
					manager.setPassword(null);
					ObjectMapper om = new ObjectMapper();
					String managerJSON = om.writeValueAsString(manager);
					pw.write(managerJSON);
					response.setStatus(GlobalVariables.RESPONSE_CODE_CREATED);
				}
			}
		}else {  // If user is not valid, sends an "unauthorized entry" code
			response.setStatus(GlobalVariables.RESPONSE_CODE_UNAUTHORIZED);
		}
		
	}
}
