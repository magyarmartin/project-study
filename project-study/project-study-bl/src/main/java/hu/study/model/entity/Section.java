package hu.study.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
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

}
