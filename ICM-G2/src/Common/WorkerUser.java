package Common;

import Common.Enums.OrganizationEnum;
import Common.Enums.UserType;

public class WorkerUser extends CollegeUser {
	int workerNum;
	OrganizationEnum organization;

	public WorkerUser(String username, String password, String firstName, String lastName, String mail,
			int workerNum, OrganizationEnum organization) {
		super(username, password, firstName, lastName, mail);
		this.workerNum = workerNum;
		this.organization = organization;
	}

}
