package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity implementation class for Entity: Model
 */
@MappedSuperclass
public class Model extends DaoImp implements Serializable {

    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false, name = "ID")
    protected Long id;
    @Basic(optional = false)
    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date create_date;
    @Basic(optional = false)
    @Column(name = "MODIFY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date modify_date;

    private static final long serialVersionUID = 1L;

    public Model() {
        super();
        this.create_date = new Date();
    }

    @Override
    public Object getId() {
        return id;
    }

    /*
         * (non-Javadoc)
         *
         * @see models.DaoImp#save()
         */
    @Override
    public <T extends DaoImp> T save() {
        modify_date = new Date();
        return super.save();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Model)) return false;
        if (!super.equals(o)) return false;

        Model model = (Model) o;

        return getId() != null ? getId().equals(model.getId()) : model.getId() == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        return result;
    }
}
