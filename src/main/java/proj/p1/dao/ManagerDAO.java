package proj.p1.dao;

import proj.p1.models.Manager;

public interface ManagerDAO {
	public Manager getManagerById(int id);
	public Manager getManagerByUser(String username);
}
