package Common;

import Common.Enums.UserType;

public class StudentUser extends CollegeUser {
	int studentNum;
	String department;

	public StudentUser(String username, String password, String firstName, String lastName, String mail, UserType type,int sutdentNum,String Department)
	{
		super( username,  password,  firstName,  lastName,  mail,  type);
		this.studentNum=studentNum;
		this.department=department;
	}

}
