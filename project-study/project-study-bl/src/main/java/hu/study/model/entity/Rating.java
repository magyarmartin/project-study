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

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "RATING" )
public class Rating implements Serializable {

    private static final long serialVersionUID = 7563703512406751320L;

    @Id
    @GeneratedValue
    @Column( name = "id" )
    private Integer id;

    @Min( 1 )
    @Max( 5 )
    @Column( name = "score" )
    private int score;

    @Column( name = "description", length = 500 )
    private String description;

    @Column( name = "creation_date" )
    private Date creationDate;

    @ManyToOne
    @JoinColumn( name = "user_id" )
    private User owner;

    @ManyToOne
    @JoinColumn( name = "target_user" )
    private User targetUser;

    @ManyToOne
    @JoinColumn( name = "target_course" )
    private Course targetCourse;

    @ManyToOne
    @JoinColumn( name = "target_section" )
    private Section targetSection;

    @ManyToOne
    @JoinColumn( name = "target_lesson" )
    private Lesson targetLesson;

}
