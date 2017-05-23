package hu.study.model.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User extends Rateable implements Serializable  {

	private static final long serialVersionUID = 7401138290924244636L;
	
	@Column(name = "first_name", length = 20)
	private String firstName;
	
	@Column(name = "last_name", length = 10)
	private String lastName;
	
	@Column(name = "email", length = 30)
	private String email;
	
	@Column(name = "password", length = 16)
	private String password;
	
	@Column(name = "description", length = 500, nullable = true)
	private String description;
	
	@Column(name = "instructor")
	private boolean instructor;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Course> subscribedCourses;
	
	@Column(name = "registration_date")
	private Date registrationDate;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "creator")
	private List<Course> ownCourses;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
	private List<Comment> comments;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isInstructor() {
		return instructor;
	}

	public void setInstructor(boolean instructor) {
		this.instructor = instructor;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public List<Course> getSubscribedCourses() {
		return subscribedCourses;
	}

	public void setSubscribedCourses(List<Course> subscribedCourses) {
		this.subscribedCourses = subscribedCourses;
	}

	public List<Course> getOwnCourses() {
		return ownCourses;
	}

	public void setOwnCourses(List<Course> ownCourses) {
		this.ownCourses = ownCourses;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}
