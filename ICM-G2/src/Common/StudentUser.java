package Common;

import Common.Enums.UserType;

public class StudentUser extends CollegeUser {
	int studentNum;
	String department;

	public StudentUser(String username, String password, String firstName, String lastName, String mail, int studentNum,
			String department) {
		super(username, password, firstName, lastName, mail);
		this.studentNum = studentNum;
		this.department = department;
	}

}
