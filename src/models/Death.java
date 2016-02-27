package models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sissoko on 23/02/2016.
 */
@Entity
@Table(name = "T_DEATHS")
public class Death extends Model {

    @Basic(optional = false)
    @Column(name = "DEATH_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date death_date;
    @Column(name = "DEATH_COUNT")
    private Integer death_count;
    @ManyToOne
    @JoinColumn(name = "BANDE_ID")
    private Bande bande;

    public Death() {
        this.death_date = new Date();
        this.death_count = 0;
    }

    public Date getDeath_date() {
        return death_date;
    }

    public void setDeath_date(Date death_date) {
        this.death_date = death_date;
    }

    public Integer getDeath_count() {
        return death_count;
    }

    public void setDeath_count(Integer death_count) {
        this.death_count = death_count;
    }

    public Bande getBande() {
        return bande;
    }

    public void setBande(Bande bande) {
        this.bande = bande;
    }

    @Override
    public String toString() {
        return String.format("Mort[%d] - %d", getId(), death_count);
    }
}
