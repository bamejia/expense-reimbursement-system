package proj.p1.dao;

import java.util.Collection;

import proj.p1.models.ReimbursementRequest;
import proj.p1.models.RequestStatus;

public interface ReimbursementRequestDAO {
	public Collection<ReimbursementRequest> getAllPendingRequests();
	public Collection<ReimbursementRequest> getPendingRequestsByEmployeeId(int id);
	public Collection<ReimbursementRequest> getResolvedRequestsByEmployeeId(int id);
	public ReimbursementRequest getRequestById(int id);
	
	public boolean createRequest(ReimbursementRequest request);
	public boolean updateRequestStatus(int requestId, RequestStatus requestStatus);
}
