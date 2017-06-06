package hu.study.model.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "USER")
@NamedQuery(name="User.findByEmail", query="SELECT u FROM User u WHERE u.email = :email")
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
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "PROGRESS",
	        joinColumns = @JoinColumn( name = "user_id", referencedColumnName = "id" ),
	        inverseJoinColumns = @JoinColumn( name = "lesson_id", referencedColumnName = "id" )
	)
	private List<Section> finishedLessons;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "PROGRESS",
	        joinColumns = @JoinColumn( name = "user_id", referencedColumnName = "id" ),
	        inverseJoinColumns = @JoinColumn( name = "section_id", referencedColumnName = "id" )
	)
	private List<Comment> finishedSections;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "PROGRESS",
	        joinColumns = @JoinColumn( name = "user_id", referencedColumnName = "id" ),
	        inverseJoinColumns = @JoinColumn( name = "course_id", referencedColumnName = "id" )
	)
	private List<Course> finishedCourses;
	
	public User() {}
	
	public User(String email, String pwd) {
		this.email = email;
		this.password = pwd;
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

	public List<Section> getFinishedLessons() {
		return finishedLessons;
	}

	public void setFinishedLessons(List<Section> finishedLessons) {
		this.finishedLessons = finishedLessons;
	}

	public List<Comment> getFinishedSections() {
		return finishedSections;
	}

	public void setFinishedSections(List<Comment> finishedSections) {
		this.finishedSections = finishedSections;
	}

	public List<Course> getFinishedCourses() {
		return finishedCourses;
	}

	public void setFinishedCourses(List<Course> finishedCourses) {
		this.finishedCourses = finishedCourses;
	}

	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password=" + password
				+ ", description=" + description + ", instructor=" + instructor + ", subscribedCourses="
				+ subscribedCourses + ", registrationDate=" + registrationDate + ", ownCourses=" + ownCourses
				+ ", comments=" + comments + ", finishedLessons=" + finishedLessons + ", finishedSections="
				+ finishedSections + ", finishedCourses=" + finishedCourses + ", id=" + id + "]";
	}

}
