package hu.study.model.entity;

import jdk.nashorn.internal.objects.NativeJava;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Data
@Entity
@Table(name = "TOKEN")
@NamedQuery(name="Token.findByToken", query="SELECT t FROM Token t WHERE t.token = :token")
public class Token implements Serializable {

    public Token() {
    }

    @Id
    @GeneratedValue
    @Column(name = "id") Integer id;

    @Column(name = "token", length = 20)
    private String token;

    @Column(name = "expiration_date", length = 20)
    private Date expirationDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
