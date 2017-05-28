package hu.study.model.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "COURSE")
public class Course extends Rateable implements Serializable {

	private static final long serialVersionUID = -1591302474273896650L;
	
	@Column(name = "name", length = 30)
	private String name;
	
	@Column(name = "description", length = 500)
	private String description;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User creator;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
	private List<Section> sections;

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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	@Override
	public String toString() {
		return "Course [name=" + name + ", description=" + description + ", creationDate=" + creationDate + ", creator="
				+ creator + ", sections=" + sections + ", id=" + id + "]";
	}

}
