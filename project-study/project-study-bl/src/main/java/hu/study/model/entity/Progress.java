package hu.study.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "PROGRESS")
public class Progress implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id") Integer id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User owner;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="course_id")
    private Course targetCourse;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="section_id")
    private Course targetSection;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="lesson_id")
    private Course targetLesson;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "completed")
    private boolean completed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Course getTargetCourse() {
        return targetCourse;
    }

    public void setTargetCourse(Course targetCourse) {
        this.targetCourse = targetCourse;
    }

    public Course getTargetSection() {
        return targetSection;
    }

    public void setTargetSection(Course targetSection) {
        this.targetSection = targetSection;
    }

    public Course getTargetLesson() {
        return targetLesson;
    }

    public void setTargetLesson(Course targetLesson) {
        this.targetLesson = targetLesson;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Progress{" +
                "id=" + id +
                ", owner=" + owner +
                ", targetCourse=" + targetCourse +
                ", targetSection=" + targetSection +
                ", targetLesson=" + targetLesson +
                ", creationDate=" + creationDate +
                ", completed=" + completed +
                '}';
    }
}
