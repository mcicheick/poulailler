package data;

import controllers.PaymentController;
import models.Payment;
import models.Transaction;

import java.util.Date;
import java.util.List;

/**
 * Created by sissoko on 13/02/2016.
 */
public class PaymentTable extends ModelTable {

    public PaymentTable() {
        this(PaymentController.getInstance().select("o from Payment o order by o.payment_date desc").getResultList());
    }

    public PaymentTable(List<Payment> payments) {
        super(payments);
        this.columnNames = new String[]{"ID", "DATE PAIEMENT", "MONTANT", "TRANSACTION"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Payment bande = (Payment) models.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return bande.getId();
            case 1:
                return bande.getPayment_date();
            case 2:
                return bande.getAmount();
            case 3:
                return bande.getTransaction();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Payment bande = (Payment) models.get(rowIndex);
        switch (columnIndex) {
            case 1:
                bande.setPayment_date((Date) aValue);
                break;
            case 2:
                bande.setAmount((Double) aValue);
                break;
            case 3:
                bande.setTransaction((Transaction) aValue);
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Long.class;
            case 1:
                return Date.class;
            case 2:
                return Double.class;
            case 3:
                return Transaction.class;
        }
        return super.getColumnClass(columnIndex);
    }

    /**
     *
     * @param rowIndex
     * @return
     */
    public Payment getRow(int rowIndex) {
        return (Payment) super.getRow(rowIndex);
    }

    public void addRow() {
        Payment bande = new Payment();
        bande.setTransaction((Transaction) model);
        models.add(bande);
        fireTableDataChanged();
    }
}
