package hu.study.model.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table( name = "COURSE", indexes = { @Index( name = "course_idx", columnList = "name", unique = true ) } )
@NamedQueries( { @NamedQuery( name = "Course.findByName", query = "select c from Course c where name = :name" ),
    @NamedQuery( name = "Course.findLikeName", query = "select c from Course c where name like :name" ) } )
public class Course implements Serializable {

    @Id
    @GeneratedValue
    @Column( name = "id" )
    Integer id;

    private static final long serialVersionUID = -1591302474273896650L;

    @Column( name = "name", length = 30 )
    private String name;

    @Column( name = "description", length = 500 )
    private String description;

    @Column( name = "creation_date" )
    private Date creationDate;

    @ManyToOne
    @JoinColumn( name = "user_id" )
    private User creator;

    @OneToMany( fetch = FetchType.LAZY, mappedBy = "course" )
    private List<Section> sections;

    @OneToMany( fetch = FetchType.LAZY, mappedBy = "targetCourse" )
    private List<Rating> ratings;

}
