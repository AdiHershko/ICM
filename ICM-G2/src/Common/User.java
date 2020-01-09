package Common;

import java.io.Serializable;

import Common.Enums.*;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 */
public class User implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2536685616674785116L;
	
	/** The username. */
	private String username;
	
	/** The password. */
	private String password;
	
	/** The first name. */
	private String firstName;
	
	/** The last name. */
	private String lastName;
	
	/** The mail. */
	private String mail;
	
	/** The role. */
	private Role role;
	
	/** The college num. */
	private int collegeNum;
	
	/** The department. */
	private String department;
	
	/** The organization. */
	private int organization;

	/**
	 * Instantiates a new user.
	 *
	 * @param username the username
	 * @param password the password
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param mail the mail
	 * @param role the role
	 */
	public User(String username, String password, String firstName, String lastName, String mail, Role role) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mail = mail;
		this.role = role;
	}

	/**
	 * Instantiates a new user.
	 *
	 * @param username the username
	 * @param password the password
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param mail the mail
	 * @param role the role
	 * @param collegeNum the college num
	 * @param department the department
	 * @param organization the organization
	 */
	public User(String username, String password, String firstName, String lastName, String mail, Role role,
			int collegeNum, String department, int organization) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mail = mail;
		this.role = role;
		this.collegeNum = collegeNum;
		this.department = department;
		this.organization = organization;
	}

	/**
	 * Gets the organization.
	 *
	 * @return the organization
	 */
	public int getOrganization() {
		return organization;
	}

	/**
	 * Sets the organization.
	 *
	 * @param organization the new organization
	 */
	public void setOrganization(int organization) {
		this.organization = organization;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the mail.
	 *
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Sets the mail.
	 *
	 * @param mail the new mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Sets the role.
	 *
	 * @param iSrole the new role
	 */
	public void setRole(Role iSrole) {
		role = iSrole;
	}

	/**
	 * Gets the college num.
	 *
	 * @return the college num
	 */
	public int getCollegeNum() {
		return collegeNum;
	}

	/**
	 * Sets the college num.
	 *
	 * @param collegeNum the new college num
	 */
	public void setCollegeNum(int collegeNum) {
		this.collegeNum = collegeNum;
	}

	/**
	 * Gets the department.
	 *
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * Sets the department.
	 *
	 * @param department the new department
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

}
