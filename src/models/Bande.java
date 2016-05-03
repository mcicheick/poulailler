package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sissoko on 11/02/2016.
 */
@Entity
@Table(name = "T_BANDES")
public class Bande extends Model {

    @Column(name = "TITLE")
    private String title;
    @Basic(optional = false)
    @Column(name = "ARRIVED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date arrived_date;
    @Column(name = "INITIAL_COUNT")
    private Integer initial_count;
    @Column(name = "PRICE")
    private Double price;

    @Column(name = "BONUS")
    private Double bonus;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bande")
    private List<Transaction> transactions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bande")
    private List<Observation> observations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bande")
    private List<Depense> depenses;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bande")
    private List<Death> deaths;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bande")
    private List<Remember> remembers;

    public Bande() {
        super();
        this.transactions = new ArrayList<>();
        this.observations = new ArrayList<>();
        this.depenses = new ArrayList<>();
        this.deaths = new ArrayList<>();
        this.arrived_date = new Date();
        this.initial_count = 0;
        this.price = 0.0;
        this.bonus = 0.02;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
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

    /**
     *
     * @param initial_count
     */
    public void setInitial_count(Integer initial_count) {
        this.initial_count = initial_count;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public Integer getRemain_count() {
        int remain_count = (int) (initial_count * (1 + bonus));
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            remain_count -= transaction.getQuantity();
        }
        return remain_count - getDeath();
    }

    public Integer getDeath() {
        Integer death_count = 0;
        for (int i = 0; i < deaths.size(); i++) {
            Death death = deaths.get(i);
            death_count += death.getDeath_count();
        }
        return death_count;
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
        return String.format("%s", title);
    }

    public Double getSold() {
        Double sold = 0.0;
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            sold += transaction.getPaid();
        }
        return sold;
    }

    public Double getBenefit() {
        return getSold() - getCost();
    }

    public Double getFixedCost() {
        if (price == null) {
            price = 0.0;
        }
        Double cost = price * initial_count;
        return cost;
    }

    public Double getCost() {
        Double spent = getSpent();
        return getFixedCost() + spent;
    }

    public Double getSpent() {
        Double spent = 0.0;
        for (int i = 0; i < depenses.size(); i++) {
            Depense depense = depenses.get(i);
            spent += depense.getTotal();
        }
        return spent;
    }

    public List<Depense> getDepenses() {
        return depenses;
    }

    public List<Observation> getObservations() {
        if (observations == null) {
            observations = new ArrayList<>();
        }
        return observations;
    }

    public List<Death> getDeaths() {
        return deaths;
    }

    public List<Remember> getRemembers() {
        return remembers;
    }

    public boolean isFinish() {
        return getRemain_count() <= 0;
    }
}
