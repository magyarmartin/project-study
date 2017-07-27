package hu.study.model.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "LESSON")
public class Lesson implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "id") Integer id;

	@Column(name = "name", length = 30)
	private String name;
	
	@Column(name = "content_text", length = 1000)
	private String contentText;
	
	@Column(name = "video_location", length = 30)
	private String videoLocation;
	
	@OneToOne
	@JoinColumn(name = "next_lesson")
	private Lesson next;
	
	@ManyToOne
	@JoinColumn(name = "section_id")
	private Section baseSection;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "target")
	private List<Comment> comments;

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

	@Override
	public String toString() {
		return "Lesson [name=" + name + ", contentText=" + contentText + ", videoLocation=" + videoLocation + ", next="
				+ next + ", baseSection=" + baseSection + ", creationDate=" + creationDate + ", comments=" + comments
				+ ", id=" + id + "]";
	}
	
}
