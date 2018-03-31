package hu.study.model.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table( name = "USER", indexes = { @Index( name = "user_idx", columnList = "email", unique = true ) } )
@NamedQuery( name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email" )
public class User implements Serializable {

    private static final long serialVersionUID = 7401138290924244636L;

    @Id
    @GeneratedValue
    @Column( name = "id" )
    Integer id;

    @NotNull
    @Column( name = "first_name", length = 20 )
    private String firstName;

    @NotNull
    @Column( name = "last_name", length = 10 )
    private String lastName;

    @NotNull
    @Column( name = "email", length = 30 )
    private String email;

    @NotNull
    @Column( name = "password", length = 100 )
    private String password;

    @Column( name = "description", length = 500, nullable = true )
    private String description;

    @NotNull
    @Column( name = "instructor" )
    private boolean instructor;

    @Column( name = "registration_date" )
    private Date registrationDate;

    @OneToMany( fetch = FetchType.LAZY, mappedBy = "creator" )
    private List<Course> ownCourses;

    @OneToMany( fetch = FetchType.LAZY, mappedBy = "owner" )
    private List<Comment> comments;

    @OneToMany( fetch = FetchType.LAZY, mappedBy = "owner" )
    private List<Rating> ratings;

    @OneToMany( fetch = FetchType.LAZY )
    @JoinTable( name = "PROGRESS", joinColumns = @JoinColumn( name = "user_id", referencedColumnName = "id" ),
        inverseJoinColumns = @JoinColumn( name = "lesson_id", referencedColumnName = "id" ) )
    private List<Section> startedLessons;

    @OneToMany( fetch = FetchType.LAZY )
    @JoinTable( name = "PROGRESS", joinColumns = @JoinColumn( name = "user_id", referencedColumnName = "id" ),
        inverseJoinColumns = @JoinColumn( name = "section_id", referencedColumnName = "id" ) )
    private List<Comment> startedSections;

    @OneToMany( fetch = FetchType.LAZY )
    @JoinTable( name = "PROGRESS", joinColumns = @JoinColumn( name = "user_id", referencedColumnName = "id" ),
        inverseJoinColumns = @JoinColumn( name = "course_id", referencedColumnName = "id" ) )
    private List<Course> startedCourses;

    @OneToOne( fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL )
    private Token token;

}
