package Common;

import Common.Enums.*;

public class User {

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String mail;
	private CollegeWorkerRole Crole;
	private String CollegeStudentDep;
	private ISWorkerRole ISrole;

	public User(String username, String password, String firstName, String lastName, String mail) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mail = mail;
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

	public ISWorkerRole getISrole() {
		return ISrole;
	}

	public void setISrole(ISWorkerRole iSrole) {
		ISrole = iSrole;
	}

}
