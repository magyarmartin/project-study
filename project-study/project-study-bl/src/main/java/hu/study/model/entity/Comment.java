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

@Entity
@Table(name = "COMMENT")
public class Comment implements Serializable {

	private static final long serialVersionUID = 3985878900892878877L;
	
	@Id
    @GeneratedValue
    @Column(name = "id") Integer id;
	
	@Column(name = "content", length = 500)
	private String content;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User owner;
	
	@ManyToOne
	@JoinColumn(name = "target")
	private Lesson target;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Lesson getTarget() {
		return target;
	}

	public void setTarget(Lesson target) {
		this.target = target;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", content=" + content + ", creationDate=" + creationDate + ", owner=" + owner
				+ ", target=" + target + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Comment)) {
			return false;
		}
		Comment other = (Comment) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
}
