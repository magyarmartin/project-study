package hu.study.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "USER")
@NamedQuery(name="User.findByEmail", query="SELECT u FROM User u WHERE u.email = :email")
public class User implements Serializable  {

	private static final long serialVersionUID = 7401138290924244636L;

	@Id
	@GeneratedValue
	@Column(name = "id") Integer id;

	@NotNull
	@Column(name = "first_name", length = 20)
	private String firstName;

	@NotNull
	@Column(name = "last_name", length = 10)
	private String lastName;

	@NotNull
	@Column(name = "email", length = 30)
	private String email;

	@NotNull
	@Column(name = "password", length = 100)
	private String password;
	
	@Column(name = "description", length = 500, nullable = true)
	private String description;

	@NotNull
	@Column(name = "instructor")
	private boolean instructor;
	
	@Column(name = "registration_date")
	private Date registrationDate;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "creator")
	private List<Course> ownCourses;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
	private List<Comment> comments;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
	private List<Rating> ratings;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "PROGRESS",
	        joinColumns = @JoinColumn( name = "user_id", referencedColumnName = "id" ),
	        inverseJoinColumns = @JoinColumn( name = "lesson_id", referencedColumnName = "id" )
	)
	private List<Section> startedLessons;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "PROGRESS",
	        joinColumns = @JoinColumn( name = "user_id", referencedColumnName = "id" ),
	        inverseJoinColumns = @JoinColumn( name = "section_id", referencedColumnName = "id" )
	)
	private List<Comment> startedSections;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "PROGRESS",
	        joinColumns = @JoinColumn( name = "user_id", referencedColumnName = "id" ),
	        inverseJoinColumns = @JoinColumn( name = "course_id", referencedColumnName = "id" )
	)
	private List<Course> startedCourses;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	private Token token;
	
	public User() {}
	
	public User(String email, String pwd) {
		this.email = email;
		this.password = pwd;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
		return startedLessons;
	}

	public void setFinishedLessons(List<Section> finishedLessons) {
		this.startedLessons = finishedLessons;
	}

	public List<Comment> getFinishedSections() {
		return startedSections;
	}

	public void setFinishedSections(List<Comment> finishedSections) {
		this.startedSections = finishedSections;
	}

	public List<Course> getFinishedCourses() {
		return startedCourses;
	}

	public void setFinishedCourses(List<Course> finishedCourses) {
		this.startedCourses = finishedCourses;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public List<Section> getStartedLessons() {
		return startedLessons;
	}

	public void setStartedLessons(List<Section> startedLessons) {
		this.startedLessons = startedLessons;
	}

	public List<Comment> getStartedSections() {
		return startedSections;
	}

	public void setStartedSections(List<Comment> startedSections) {
		this.startedSections = startedSections;
	}

	public List<Course> getStartedCourses() {
		return startedCourses;
	}

	public void setStartedCourses(List<Course> startedCourses) {
		this.startedCourses = startedCourses;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}
}
