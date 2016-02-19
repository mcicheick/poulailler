package data;

import controllers.DepenseController;
import models.Depense;
import models.Bande;

import java.util.Date;
import java.util.List;

/**
 * Created by sissoko on 13/02/2016.
 */
public class DepenseTable extends ModelTable {

    public DepenseTable() {
        this(DepenseController.getInstance().select("o from Depense o order by o.depense_date desc").getResultList());
    }

    public DepenseTable(List<Depense> depenses) {
        super(depenses);
        this.columnNames = new String[]{"ID", "DATE DEPENSE", "MONTANT", "BANDE", "DESCRIPTION"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Depense depense = (Depense) models.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return depense.getId();
            case 1:
                return depense.getDepense_date();
            case 2:
                return depense.getAmount();
            case 3:
                return depense.getBande();
            case 4:
                return depense.getDescription();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Depense depense = (Depense) models.get(rowIndex);
        switch (columnIndex) {
            case 1:
                depense.setDepense_date((Date) aValue);
                break;
            case 2:
                depense.setAmount((Double) aValue);
                break;
            case 3:
                depense.setBande((Bande) aValue);
                break;
            case 4:
                depense.setDescription((String) aValue);
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
                return Double.class;
            case 3:
                return Bande.class;
            case 4:
                return String.class;
        }
        return super.getColumnClass(columnIndex);
    }

    /**
     *
     * @param rowIndex
     * @return
     */
    public Depense getRow(int rowIndex) {
        return (Depense) super.getRow(rowIndex);
    }

    public void addRow() {
        Depense depense = new Depense();
        depense.setBande((Bande) model);
        models.add(depense);
        fireTableDataChanged();
    }
}
