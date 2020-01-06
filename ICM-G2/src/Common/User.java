package Common;

import java.io.Serializable;

import Common.Enums.*;

public class User implements Serializable {

	private static final long serialVersionUID = 2536685616674785116L;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String mail;
	private Role role;
	private int collegeNum;
	private String department;
	private int organization;
	

	
	public User(String username, String password, String firstName, String lastName, String mail, Role role) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mail = mail;
		this.role = role;
	}
	
	public User(String username, String password, String firstName, String lastName, String mail, Role role,int collegeNum,String department,int organization) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mail = mail;
		this.role = role;
		this.collegeNum=collegeNum;
		this.department=department;
		this.organization=organization;
	}
	

	public int getOrganization() {
		return organization;
	}

	public void setOrganization(int organization) {
		this.organization = organization;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role iSrole) {
		role = iSrole;
	}
	public int getCollegeNum() {
		return collegeNum;
	}

	public void setCollegeNum(int collegeNum) {
		this.collegeNum = collegeNum;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

}
