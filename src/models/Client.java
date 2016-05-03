package models;

import controllers.PaymentController;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sissoko on 10/02/2016.
 */

@Entity
@Table(name = "T_CLIENTS")
public class Client extends Model {

    @Column(name = "FIRST_NAME")
    private String first_name;
    @Column(name = "LAST_NAME")
    private String last_name;
    @Column(name = "PHONE", nullable = false)
    private String phone;
    @Column(name = "ADDRESS")
    private String address;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private List<Transaction> transactions;

    public Client() {
        super();
        transactions = new ArrayList<>();
    }

    public List<Payment> getPayments() {
        return PaymentController.getInstance().select("p from Payment p where p.client_id = ?1", getId()).getResultList();
    }
    /**
     *
     * @return
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     *
     * @param first_name
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     *
     * @return
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     *
     * @param last_name
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     *
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        String format = "";
        if(first_name != null) {
            format = String.format("%s %s", first_name, last_name);
        } else {
            format = phone;
        }
        return format;
    }
}
