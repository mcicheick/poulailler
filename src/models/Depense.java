package models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sissoko on 19/02/2016.
 */
@Entity
@Table(name = "T_DESPENSES")
public class Depense extends Model {
    @ManyToOne
    private Bande bande;
    @Column(name = "AMOUNT")
    private Double amount;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DEPENSE_DATE")
    private Date depense_date;
    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    public Depense() {
        super();
        this.amount = 0.0;
        this.depense_date = new Date();
    }

    public Bande getBande() {
        return bande;
    }

    public void setBande(Bande bande) {
        this.bande = bande;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDepense_date() {
        return depense_date;
    }

    public void setDepense_date(Date depense_date) {
        this.depense_date = depense_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return String.format("Dépense[%d] - %.2f", getId(), amount);
    }
}
