package hu.study.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "SECTION")
public class Section implements Serializable {

	private static final long serialVersionUID = -371066170686399849L;

	@Id
	@GeneratedValue
	@Column(name = "id") Integer id;
	
	@Column(name = "name", length = 30)
	private String name;
	
	@Column(name = "description", length = 500)
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "baseSection")
	private List<Lesson> lessons;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public List<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}

	@Override
	public String toString() {
		return "Section [name=" + name + ", description=" + description + ", course=" + course + ", creationDate="
				+ creationDate + ", lessons=" + lessons + ", id=" + id + "]";
	}
	
}
