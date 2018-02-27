package hu.study.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Data
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

}
