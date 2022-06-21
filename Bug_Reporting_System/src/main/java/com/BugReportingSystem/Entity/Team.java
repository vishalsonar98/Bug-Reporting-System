package com.BugReportingSystem.Entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;

@Entity
@Table(name="team")
public class Team {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="name")
	private String teamName;
	
	@ManyToMany
	private List<User> user;
	
	@ManyToMany
	private List<Project> project;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

	

	public List<Project> getProject() {
		return project;
	}

	public void setProject(List<Project> project) {
		this.project = project;
	}

	public Team() {
		super();
		
	}

	public Team(int id, String teamName, List<User> user, List<Project> project) {
		super();
		this.id = id;
		this.teamName = teamName;
		this.user = user;
		this.project = project;
	}

	@Override
	public String toString() {
		return "Team [id=" + id + ", teamName=" + teamName + ", user=" + user + ", project=" + project + "]";
	}

	
}
