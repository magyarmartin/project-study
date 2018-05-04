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

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "COMMENT" )
public class Comment implements Serializable {

    private static final long serialVersionUID = 3985878900892878877L;

    @Id
    @GeneratedValue
    @Column( name = "id" )
    Integer id;

    @Column( name = "content", length = 500 )
    private String content;

    @Column( name = "creation_date" )
    private Date creationDate;

    @ManyToOne
    @JoinColumn( name = "user_id" )
    private User owner;

    @ManyToOne
    @JoinColumn( name = "target" )
    private Lesson target;

}
