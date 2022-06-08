package com.BugReportingSystem.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserRole {
	@Id
	@Column(name="user_type_id")
	private int userTypeId;
	@Column(name="user_type")
	private String userType;
	
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
	public UserRole(int userTypeId, String userType) {
		super();
		this.userTypeId = userTypeId;
		this.userType = userType;
	}
	public UserRole() {
		
	}
	@Override
	public String toString() {
		return "UserRole [userTypeId=" + userTypeId + ", userType=" + userType + "]";
	}
	
}
