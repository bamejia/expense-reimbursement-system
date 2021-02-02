package proj.p1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ReimbursementRequest {
	private int id;
	private int employeeId;
	private int amount;
	private RequestStatus status = RequestStatus.PENDING;
	
	@JsonIgnore
	private Employee employee = null;
	
	public ReimbursementRequest() {
		super();
	}

	public ReimbursementRequest(int employeeId, int amount, RequestStatus status) {
		super();
		this.employeeId = employeeId;
		this.amount = amount;
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + ((employee == null) ? 0 : employee.hashCode());
		result = prime * result + employeeId;
		result = prime * result + id;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReimbursementRequest other = (ReimbursementRequest) obj;
		if (amount != other.amount)
			return false;
		if (employee == null) {
			if (other.employee != null)
				return false;
		} else if (!employee.equals(other.employee))
			return false;
		if (employeeId != other.employeeId)
			return false;
		if (id != other.id)
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "ReimbursementRequest [id=" + id + ", employeeId=" + employeeId + ", amount=" + amount + ", status="
				+ status + ", employee=" + employee + "]";
	}
	
}
