package models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sissoko on 18/02/2016.
 */
@Entity
@Table(name = "T_OBSERVATIONS")
public class Observation extends Model {

    @ManyToOne
    private Bande bande;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OBSERVATION_DATE")
    private Date observation_date;
    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    public Observation() {
        super();
        this.observation_date = new Date();
    }

    public Bande getBande() {
        return bande;
    }

    public void setBande(Bande bande) {
        this.bande = bande;
    }

    public Date getObservation_date() {
        return observation_date;
    }

    public void setObservation_date(Date observation_date) {
        this.observation_date = observation_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return String.format("Observation[%d] - %s", getId(), new SimpleDateFormat("dd/MM/yyyy").format(observation_date));
    }
}
