package com.BugReportingSystem.Entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
	@Email()
	private String email;
	@NotBlank(message = "*Password Required")
	private String password;
	
	/*
	 * One to one mapping with UserRole entity to store the user Role information in
	 * user table
	 */
	@OneToOne
	@JoinColumn(name="userTypeId")
	private UserRole userTypeId;
	
	@ManyToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//	@JoinTable(name="user_team",joinColumns = {@JoinColumn(name="user_id")},inverseJoinColumns = {@JoinColumn(name="team_id")})
	private List<Team> team;
	/*
	 * Getter and Setter method to get and set data of fields.
	 * @ return corresponding field
	 */
	public int getId() {
		return id ;
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
	

	public List<Team> getTeam() {
		return team;
	}


	public void setTeam(List<Team> team) {
		this.team = team;
	}


	/*
	 * parameterized constructor to set data in fields of entity
	 */
	
	
	public User(int id, String firstName, String lastName, @Email String email, String password, UserRole userTypeId,
			List<Team> team) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.userTypeId = userTypeId;
		this.team = team;
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
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", userTypeId=" + userTypeId + ", team=" + team + "]";
	}

	
	
}
