package models;

import javax.persistence.*;

/**
 * Created by sissoko on 18/02/2016.
 */
@Entity
@Table(name = "T_OBSERVATIONS")
public class Observation extends Model {

    @ManyToOne
    private Bande bande;
    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    public Observation() {
        super();
    }

    public Bande getBande() {
        return bande;
    }

    public void setBande(Bande bande) {
        this.bande = bande;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
