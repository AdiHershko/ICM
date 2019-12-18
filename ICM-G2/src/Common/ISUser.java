package Common;

import Common.Enums.ISWorkerRole;

public class ISUser extends User {
	private ISWorkerRole role;
	public ISUser(String username, String password, String firstName, String lastName, String mail,ISWorkerRole role) {
		super(username, password, firstName, lastName, mail);
		this.role=role;
	}

	public ISWorkerRole getRole() {
		return role;
	}

	public void setRole(ISWorkerRole role) {
		this.role = role;
	}

}
