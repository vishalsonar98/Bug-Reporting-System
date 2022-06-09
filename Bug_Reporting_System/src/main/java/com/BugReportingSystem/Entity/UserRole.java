package com.BugReportingSystem.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


/*
* UserRole entity to store information of user role inside database
*/
@Entity
public class UserRole {
	/*
	 * defining fields of UserRole entity
	 */
	@Id
	@Column(name="user_type_id")
	private int userTypeId;
	@Column(name="user_type")
	private String userType;
	
	/*
	 * Getter and Setter method to get and set data of fields.
	 * @ return corresponding field
	 */
	public int getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(int userTypeId) {
		this.userTypeId = userTypeId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	/*
	 * parameterized constructor to set data in fields of entity
	 */
	public UserRole(int userTypeId, String userType) {
		super();
		this.userTypeId = userTypeId;
		this.userType = userType;
	}
	/*
	 * default constructor
	 */
	public UserRole() {
		
	}
	
	/*
	 * overriding toString method to print data
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		return "UserRole [userTypeId=" + userTypeId + ", userType=" + userType + "]";
	}
	
}
