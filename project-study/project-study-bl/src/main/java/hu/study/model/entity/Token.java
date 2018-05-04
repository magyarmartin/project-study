package hu.study.model.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "TOKEN" )
@NamedQuery( name = "Token.findByToken", query = "SELECT t FROM Token t WHERE t.token = :token" )
public class Token implements Serializable {

    @Id
    @GeneratedValue
    @Column( name = "id" )
    Integer id;

    @Column( name = "token", length = 20 )
    private String token;

    @Column( name = "expiration_date", length = 20 )
    private Date expirationDate;

    @OneToOne
    @JoinColumn( name = "user_id" )
    private User user;

}
