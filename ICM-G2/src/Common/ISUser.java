package Common;


import Common.Enums.Role;

public class ISUser extends User {
	
	public ISUser(String username, String password, String firstName, String lastName, String mail,Role role,int UserId) {
		super(username, password, firstName, lastName, mail,role);
		
	}

}
