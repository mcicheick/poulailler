package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by sissoko on 11/02/2016.
 */
@Entity
@Table(name = "T_PAYMENTS")
public class Payment extends Model implements Serializable {

    @Column(name = "AMOUNT")
    private Double amount;
    @Column(name = "PAYMENT_DATE")
    private Date payment_date;
    @ManyToOne
    @JoinColumn(name = "TRANSACTION_ID")
    private Transaction transaction;

    public Payment() {
        super();
        this.payment_date = new Date();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(Date payment_date) {
        this.payment_date = payment_date;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return String.format("Payment[%d] - %.2f", getId(), getAmount());
    }
}
