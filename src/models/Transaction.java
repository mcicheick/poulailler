package models;

import controllers.PaymentController;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sissoko on 11/02/2016.
 */
@Entity
@Table(name = "T_TRANSACTIONS")
public class Transaction extends Model {

    @Basic(optional = false)
    @Column(name = "TRANSACTION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transaction_date;
    @Column(name = "UNIT_PRICE")
    private Double unit_price;
    @Column(name = "PRICE_BY_KILO")
    private Double price_by_kilo;
    @Column(name = "QUANTITY")
    private Double quantity;
    @Column(name = "WEIGHT")
    private Double weight;

    @ManyToOne
    @JoinColumn(name = "BANDE_ID")
    private Bande bande;
    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaction")
    private List<Payment> payments;

    public Transaction() {
        super();
        this.transaction_date = new Date();
        this.payments = new ArrayList<>();
        this.unit_price = 0d;
        this.price_by_kilo = 0d;
        this.quantity = 0d;
        this.weight = 0.0;
    }

    @Override
    public <T extends DaoImp> void create() {
        super.create();
        if(bande != null) {
            bande.updateQuantity(quantity);
        }
    }

    public Double getCost() {
        return quantity * unit_price + weight * price_by_kilo;
    }

    public Double getPaid() {
        Double paid = 0.0;
        if(getId() != null) {
            List<Payment> payments = PaymentController.getInstance()
                    .select("p from Payment p where p.transaction = ?1", this).getResultList();
            for (int i = 0; i < payments.size(); i++) {
                paid += payments.get(i).getAmount();
            }
        }
        return paid;
    }

    public Double getRemainAmount() {
        return getCost() - getPaid();
    }

    public Date getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(Date transaction_date) {
        this.transaction_date = transaction_date;
    }

    public Double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Double unit_price) {
        this.unit_price = unit_price;
    }

    public Double getPrice_by_kilo() {
        return price_by_kilo;
    }

    public void setPrice_by_kilo(Double price_by_kilo) {
        this.price_by_kilo = price_by_kilo;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Bande getBande() {
        return bande;
    }

    public void setBande(Bande bande) {
        this.bande = bande;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Payment> getPayments() {
        return PaymentController.getInstance().select("p from Payment p where p.transaction = ?1", this).getResultList();
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return String.format("Transaction[%d] du %s %s", getId(), new SimpleDateFormat("dd/MM/yyyy").format(transaction_date), client != null ? "de " + client.toString() : "");
    }
}
