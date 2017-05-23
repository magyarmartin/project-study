package hu.study.model.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "RATING")
public class Rating implements Serializable {

	private static final long serialVersionUID = 7563703512406751320L;
	
	@Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
	
	@Min(1)
	@Max(5)
	@Column(name = "score")
	private int score;
	
	@Column(name = "description", length = 500)
	private String description;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User owner;
	
	@ManyToOne
	@JoinColumn(name = "target")
	private Rateable target;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Rateable getTarget() {
		return target;
	}

	public void setTarget(Rateable target) {
		this.target = target;
	}

}
