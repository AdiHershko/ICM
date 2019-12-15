package Common;

import Common.Enums.UserRoles;

public class User {
	
	private String id;
	private String password;
	private int numID;
	private String firstName;
	private String lastName;
	private UserRoles role;
	
	public User(String id,String password)
	{
		this.id=id;
		this.password=password;
	}
	
	public User(String id,String password,String firstName,String lastName,UserRoles role)
	{
		this.id=id;
		this.password=password;
		this.firstName=firstName;
		this.lastName=lastName;
		this.role=role;
	}
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getNumID() {
		return numID;
	}

	public void setNumID(int numID) {
		this.numID = numID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UserRoles getRole() {
		return role;
	}

	public void setRole(UserRoles role) {
		this.role = role;
	}



}
