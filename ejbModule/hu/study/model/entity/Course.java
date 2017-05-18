package hu.study.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COURSE")
public class Course implements Serializable {

	private static final long serialVersionUID = -1591302474273896650L;
	
	@Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
	
	@Column(name = "name", length = 30)
	private String name;
	
	@Column(name = "description", length = 500)
	private String description;
	
	@Column(name = "user")
	private User user;
	
	@Column(name = "creation_date")
	private Date creationDate
}
