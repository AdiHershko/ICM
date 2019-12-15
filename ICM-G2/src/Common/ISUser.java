package Common;

import Common.Enums.UserType;

public class ISUser extends User {

	public ISUser(String username, String password, String firstName, String lastName, String mail, UserType type) {
		super(username, password, firstName, lastName, mail, type);
		
	}

}
