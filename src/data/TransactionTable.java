package data;

import controllers.TransactionController;
import models.Bande;
import models.Client;
import models.Transaction;

import java.util.Date;
import java.util.List;

/**
 * Created by sissoko on 13/02/2016.
 */
public class TransactionTable extends ModelTable {

    public TransactionTable() {
        this(TransactionController.getInstance().select("o from Transaction o order by o.transaction_date desc").getResultList());
    }

    public TransactionTable(List models) {
        super(models);
        this.columnNames = new String[]{"ID", "DATE TRANSACTION", "PRIX UNITAIRE", "PRIX PAR KILO", "QUANTITÉ",
                "POIDS", "BANDE", "CLIENT", "RESTANT"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Transaction transaction = (Transaction) models.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return transaction.getId();
            case 1:
                return transaction.getTransaction_date();
            case 2:
                return transaction.getUnit_price();
            case 3:
                return transaction.getPrice_by_kilo();
            case 4:
                return transaction.getQuantity();
            case 5:
                return transaction.getWeight();
            case 6:
                return transaction.getBande();
            case 7:
                return transaction.getClient();
            case 8:
                return transaction.getRemainAmount();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Transaction transaction = (Transaction) models.get(rowIndex);
        switch (columnIndex) {
            case 1:
                transaction.setTransaction_date((Date) aValue);
                break;
            case 2:
                transaction.setUnit_price((Double) aValue);
                break;
            case 3:
                transaction.setPrice_by_kilo((Double) aValue);
                break;
            case 4:
                transaction.setQuantity((Double) aValue);
                break;
            case 5:
                transaction.setWeight((Double) aValue);
                break;
            case 6:
                transaction.setBande((Bande) aValue);
                break;
            case 7:
                transaction.setClient((Client) aValue);
                break;
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
            case 3:
            case 4:
            case 5:
                return Double.class;
            case 6:
                return Bande.class;
            case 7:
                return Client.class;
            case 8:
                return Double.class;
        }
        return super.getColumnClass(columnIndex);
    }

    /**
     *
     * @param rowIndex
     * @return
     */
    public Transaction getRow(int rowIndex) {
        return (Transaction) super.getRow(rowIndex);
    }

    public void addRow() {
        Transaction bande = new Transaction();
        if(model != null) {
            if (model instanceof Client)
                bande.setClient((Client) model);
            if(model instanceof Bande) {
                bande.setBande((Bande) model);
            }
        }
        models.add(bande);
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return super.isCellEditable(rowIndex, columnIndex) && columnIndex < 8;
    }
}
