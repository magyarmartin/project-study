package hu.study.model.entity;

import jdk.nashorn.internal.objects.NativeJava;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

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

    public Token(String tokenString) {
        this.token = tokenString;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", user=" + user +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
