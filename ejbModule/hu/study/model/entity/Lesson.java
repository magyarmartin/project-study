package hu.study.model.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LESSON")
public class Lesson extends Rateable implements Serializable {

	@Column(name = "name", length = 30)
	private String name;
	
	@Column(name = "content_text", length = 1000)
	private String contentText;
	
	@Column(name = "video_location", length = 30)
	private String videoLocation;
	
	@OneToOne
	@JoinColumn(name = "next")
	private Lesson next;
	
	@ManyToOne
	@JoinColumn(name = "section_id")
	private Section baseSection;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "target")
	private List<Comment> comments;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public String getVideoLocation() {
		return videoLocation;
	}

	public void setVideoLocation(String videoLocation) {
		this.videoLocation = videoLocation;
	}

	public Lesson getNext() {
		return next;
	}

	public void setNext(Lesson next) {
		this.next = next;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
}
