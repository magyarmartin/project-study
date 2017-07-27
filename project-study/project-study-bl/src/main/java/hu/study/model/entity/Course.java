package hu.study.model.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "COURSE")
@NamedQueries({
		@NamedQuery(name="Course.findByName", query="select c from Course c where name = :name"),
		@NamedQuery(name="Course.findLikeName", query="select c from Course c where name like :name")
})
public class Course implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "id") Integer id;

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
