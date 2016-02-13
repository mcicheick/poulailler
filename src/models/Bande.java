package models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sissoko on 11/02/2016.
 */
@Entity
@Table(name = "T_BANDES")
public class Bande extends Model {

    @Basic(optional = false)
    @Column(name = "ARRIVED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date arrived_date;
    @Column(name = "INITIAL_COUNT")
    private Integer initial_count;
    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "id")
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bande")
    private List<Transaction> transactions;

    public Bande() {
        super();
    }

    public Date getArrived_date() {
        return arrived_date;
    }

    public void setArrived_date(Date arrived_date) {
        this.arrived_date = arrived_date;
    }

    public Integer getInitial_count() {
        return initial_count;
    }

    public void setInitial_count(Integer initial_count) {
        this.initial_count = initial_count;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return String.format("Bande[%d] du %s", getId(), new SimpleDateFormat("dd/MM/yyyy").format(arrived_date));
    }
}
