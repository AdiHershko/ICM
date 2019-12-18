package Common;

import java.io.Serializable;

import Common.Enums.*;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2536685616674785116L;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String mail;
	private CollegeWorkerRole Crole;
	private String CollegeStudentDep;
	private Role role;
	private String permissions;
	//private int userID;

	public User(String username, String password, String firstName, String lastName, String mail,Role role) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mail = mail;
		this.role=role;
		
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public CollegeWorkerRole getCrole() {
		return Crole;
	}

	public void setCrole(CollegeWorkerRole crole) {
		Crole = crole;
	}

	public String getCollegeStudentDep() {
		return CollegeStudentDep;
	}

	public void setCollegeStudentDep(String collegeStudentDep) {
		CollegeStudentDep = collegeStudentDep;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role iSrole) {
		role = iSrole;
	}
	

}
