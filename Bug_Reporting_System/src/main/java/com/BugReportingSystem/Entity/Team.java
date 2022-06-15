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

	public Team(int id, String teamName, List<User> user) {
		super();
		this.id = id;
		this.teamName = teamName;
		this.user = user;
	}

	public Team() {
		super();
		
	}

	@Override
	public String toString() {
		return "Team [id=" + id + ", teamName=" + teamName + ", user=" + user + "]";
	}

}
