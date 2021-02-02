import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import proj.p1.services.EmployeeServices;
import proj.p1.services.ManagerServices;

public class P1Tests {

		private EmployeeServices es = new EmployeeServices();
		private ManagerServices ms = new ManagerServices();
		
		@Test
		public void testCreatingEmployeeRequest() {
			assertTrue(es.createReimbursementRequest(1, 500));
		}
		
		@Test
		public void testAllPendingRequest() {
			assertNotEquals(ms.getAllPendingRequests(), null);
		}
		
		@Test
		public void testCreatingCustomerRequest() {
			assertNotEquals(es.getResolvedRequests(2), null);
		}
}
