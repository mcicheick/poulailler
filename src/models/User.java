package models;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sissoko on 11/02/2016.
 */
@Entity
@Table(name = "T_USERS")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
})
public class User extends Model {

    @Column(name = "FIRST_NAME")
    private String first_name;
    @Column(name = "LAST_NAME")
    private String last_name;
    @Column(name = "EMAIL", nullable = false, unique = true)
    @Index(name = "EMAILS_INDEXES")
    private String email;
    @Column(name = "PASSWORD")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Bande> bandes;

    public User() {
        super();
        bandes = new ArrayList<>();
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Bande> getBandes() {
        return bandes;
    }

    public void setBandes(List<Bande> bandes) {
        this.bandes = bandes;
    }

    @Override
    public String toString() {
        return email;
    }
}
