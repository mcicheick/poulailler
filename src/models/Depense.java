package models;

import javax.persistence.*;

import java.util.Date;

/**
 * Created by sissoko on 19/02/2016.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_DESPENSES")
public class Depense extends Model {
	@ManyToOne
	private Bande bande;

    @ManyToOne
    private Category category;

	@Column(name = "UNIT_PRICE")
	private Double unit_price;

	@Column(name = "QUANTITY")
	private Integer quantity;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DEPENSE_DATE")
	private Date depense_date;
	
	@Column(name = "DESCRIPTION")
	@Lob
	private String description;

	public Depense() {
		super();
		this.unit_price = 0.0;
        this.quantity = 0;
		this.depense_date = new Date();
	}

	public Bande getBande() {
		return bande;
	}

	/**
	 * 
	 * @param bande
	 */
	public void setBande(Bande bande) {
		this.bande = bande;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getUnit_price() {
		return unit_price;
	}

    /**
     *
     * @param unit_price
     */
	public void setUnit_price(Double unit_price) {
		this.unit_price = unit_price;
	}

	public Date getDepense_date() {
		return depense_date;
	}

    /**
     *
     * @param depense_date
     */
	public void setDepense_date(Date depense_date) {
		this.depense_date = depense_date;
	}

	public String getDescription() {
		return description;
	}

    /**
     *
     * @param description
     */
	public void setDescription(String description) {
		this.description = description;
	}

    public Double getTotal() {
        Double total = 0.0;
        total = quantity * unit_price;
        return total;
    }

	@Override
	public String toString() {
		return String.format("Dépense[%d] - %.2f", getId(), getTotal());
	}
}
