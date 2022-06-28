package com.BugReportingSystem.Entity;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Bug 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="bug_title")
	private String bugTitle;
	@Column(name="bug_status")
	private String bugStatus;
	
	@Column(name="bug_desc")
	private String bugDiscription;
	@Column(name="bug_creation_date")
	@Temporal(TemporalType.DATE)
	private Date bugCreationDate=new Date();
	
	
	/*
	 * @Lob private byte[] attachment;
	 */
	
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="project_id")
	private Project projectId;
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="tester_id")
	private User testerId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="devloper_id")
	private User devloperId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBugTitle() {
		return bugTitle;
	}

	public void setBugTitle(String bugTitle) {
		this.bugTitle = bugTitle;
	}

	public String getBugStatus() {
		return bugStatus;
	}

	public void setBugStatus(String bugStatus) {
		this.bugStatus = bugStatus;
	}

	public String getBugDiscription() {
		return bugDiscription;
	}

	public void setBugDiscription(String bugDiscription) {
		this.bugDiscription = bugDiscription;
	}

	public Date getBugCreationDate() {
		return bugCreationDate;
	}

	public void setBugCreationDate(Date bugCreationDate) {
		this.bugCreationDate = bugCreationDate;
	}

	public Project getProjectId() {
		return projectId;
	}

	public void setProjectId(Project projectId) {
		this.projectId = projectId;
	}

	public User getTesterId() {
		return testerId;
	}

	public void setTesterId(User testerId) {
		this.testerId = testerId;
	}

	public User getDevloperId() {
		return devloperId;
	}

	public void setDevloperId(User devloperId) {
		this.devloperId = devloperId;
	}
	
	public Bug(int id, String bugTitle, String bugStatus, String bugDiscription, Date bugCreationDate,
			Project projectId, User testerId, User devloperId) {
		super();
		this.id = id;
		this.bugTitle = bugTitle;
		this.bugStatus = bugStatus;
		this.bugDiscription = bugDiscription;
		this.bugCreationDate = bugCreationDate;
		this.projectId = projectId;
		this.testerId = testerId;
		this.devloperId = devloperId;
	}

	public Bug() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Bug [id=" + id + ", bugTitle=" + bugTitle + ", bugStatus=" + bugStatus + ", bugDiscription="
				+ bugDiscription + ", bugCreationDate=" + bugCreationDate + ", projectId=" + projectId + ", testerId="
				+ testerId + ", devloperId=" + devloperId + "]";
	}
	
	
}
