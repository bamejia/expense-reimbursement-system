package proj.p1.models;

import java.util.Collection;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Employee extends UserAccount{
	private Collection<ReimbursementRequest> reimbursementRequests;

	public Employee() {
		super();
//		reimbursementRequests = new HashSet<ReimbursementRequest>();
	}

	public Employee(String username, String password, String firstName, String lastName) {
		super(username, password, firstName, lastName);
		reimbursementRequests = new HashSet<ReimbursementRequest>();
	}

	@JsonIgnore
	public Collection<ReimbursementRequest> getReimbursementRequests() {
		return reimbursementRequests;
	}

	public void setReimbursementRequests(Collection<ReimbursementRequest> reinbursementRequests) {
		this.reimbursementRequests = reinbursementRequests;
	}
	
}
