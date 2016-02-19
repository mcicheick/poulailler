package models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    @Column(name = "REMAIN_COUNT")
    private Integer remain_count;
    @Column(name = "DISEASE")
    private Integer disease;
    @Column(name = "PRICE")
    private Double price;
    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "id")
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bande")
    private List<Transaction> transactions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bande")
    private List<Observation> observations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bande")
    private List<Depense> depenses;

    public Bande() {
        super();
        this.transactions = new ArrayList<>();
        this.arrived_date = new Date();
        this.initial_count = 0;
        this.disease = 0;
        this.remain_count = 0;
        this.price = 0.0;
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

    public Integer getRemain_count() {
        return remain_count;
    }

    public void setRemain_count(Integer remain_count) {
        this.remain_count = remain_count;
    }

    public Integer getDisease() {
        return disease;
    }

    public void setDisease(Integer disease) {
        this.disease = disease;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public void updateQuantity(Double quantity) {
        if (quantity != null) {
            this.remain_count -= quantity.intValue();
            save();
        }
    }

    public Double getSold() {
        Double sold = 0.0;
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            sold += transaction.getPaid();
        }
        return sold;
    }

    public Double getCost() {
        if(price == null) {
            price = 0.0;
        }
        Double cost = price * initial_count;
        Double spent = getSpent();
        return cost + spent;
    }

    public Double getSpent() {
        Double spent = 0.0;
        for (int i = 0; i < depenses.size(); i++) {
            Depense depense = depenses.get(i);
            spent += depense.getAmount();
        }
        return spent;
    }

    public List<Depense> getDepenses() {
        return depenses;
    }

    public List<Observation> getObservations() {
        return observations;
    }
}
