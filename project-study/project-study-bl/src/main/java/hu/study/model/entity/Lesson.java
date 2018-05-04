package hu.study.model.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "LESSON" )
public class Lesson implements Serializable {

    @Id
    @GeneratedValue
    @Column( name = "id" )
    Integer id;

    @Column( name = "name", length = 30 )
    private String name;

    @Column( name = "content_text", length = 1000 )
    private String contentText;

    @Column( name = "video_location", length = 30 )
    private String videoLocation;

    @OneToOne
    @JoinColumn( name = "next_lesson" )
    private Lesson next;

    @ManyToOne
    @JoinColumn( name = "section_id" )
    private Section baseSection;

    @Column( name = "creation_date" )
    private Date creationDate;

    @OneToMany( fetch = FetchType.LAZY, mappedBy = "target" )
    private List<Comment> comments;

}
