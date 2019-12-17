package Common;

import Common.Enums.ISWorkerRole;

public class ISUser extends User {
	private ISWorkerRole Role;
	public ISUser(String username, String password, String firstName, String lastName, String mail) {
		super(username, password, firstName, lastName, mail);
		
	}
	public ISWorkerRole getSystem() {
		return Role;
	}

	public void setSystem(ISWorkerRole system) {
		this.Role = Role;
	}

}
