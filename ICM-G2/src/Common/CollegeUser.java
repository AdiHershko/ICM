package Common;

import java.util.Date;
import java.util.ArrayList;

import Common.Enums.Role;
import Common.Enums.SystemENUM;
import Common.Enums.UserType;

public class CollegeUser extends User {


	ArrayList<Request> openedRequests;

	public CollegeUser(String username, String password, String firstName, String lastName, String mail,Role role) {
		super(username, password, firstName, lastName, mail,role);
	}



	/*public void OpenRequest(User requestor, SystemENUM system, String description, String changes, String date)
	{
		if (openedRequests==null)
		{
			openedRequests=new ArrayList<>();
		}
		Request r = new Request(this,system,description,changes,new Date().toString());
		openedRequests.add(r);
	}*/

}
