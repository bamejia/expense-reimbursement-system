package proj.p1.dao;

import java.util.Collection;

import proj.p1.models.Employee;

public interface EmployeeDAO {
	public Collection<Employee> getEmployeesWithPendingRequests();
	public Employee getEmployeeById(int id);
	public Employee getEmployeeByUser(String username);
}
