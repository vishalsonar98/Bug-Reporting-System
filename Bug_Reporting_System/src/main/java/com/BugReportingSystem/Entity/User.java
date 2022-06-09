package com.BugReportingSystem.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/*
* User entity to store information of user inside database
*/
@Entity
/*
 * changing name of table inside database
 */
@Table(name="user")
public class User {
	/*
	 * defining fields of user entity
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@Column(unique = true)
	private String email;
	private String password;
	/*
	 * One to one mapping with UserRole entity to store the user Role information in
	 * user table
	 */
	@OneToOne
	@JoinColumn(name="userTypeId")
	private UserRole userTypeId;

	/*
	 * Getter and Setter method to get and set data of fields.
	 * @ return corresponding field
	 */
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public UserRole getUserTypeId() {
		return userTypeId;
	}


	public void setUserTypeId(UserRole userTypeId) {
		this.userTypeId = userTypeId;
	}

	/*
	 * parameterized constructor to set data in fields of entity
	 */
	public User(int id, String firstName, String lastName, String email, String password, UserRole userTypeId) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.userTypeId = userTypeId;
	}

	/*
	 * default constructor
	 */
	public User() {
		
	}

	/*
	 * overriding toString method to print data
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", userTypeId=" + userTypeId + "]";
	}
	
}
