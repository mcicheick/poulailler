package data;

import controllers.DepenseController;
import models.Category;
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
        // "DATE DEPENSE", "MONTANT", "BANDE", "CATEGORY", "DESCRIPTION"
        this.columnNames = new String[]{"ID", "CATEGORY", "BANDE", "DATE DEPENSE", "QUANTITE", "PRIX UNITAIRE", "TOTAL", "DESCRIPTION"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Depense depense = (Depense) models.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return depense.getId();
            case 1:
                return depense.getCategory();
            case 2:
                return depense.getBande();
            case 3:
                return depense.getDepense_date();
            case 4:
                return depense.getQuantity();
            case 5:
                return depense.getUnit_price();
            case 6:
                return depense.getTotal();
            case 7:
                return depense.getDescription();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Depense depense = (Depense) models.get(rowIndex);
        // "ID", "CATEGORY", "BANDE", "DATE DEPENSE", "QUANTITE", "PRIX UNITAIRE", "TOTAL", "DESCRIPTION"
        switch (columnIndex) {
            case 1:
                depense.setCategory((Category) aValue);
                break;
            case 2:
                depense.setBande((Bande) aValue);
                break;
            case 3:
                depense.setDepense_date((Date) aValue);
                break;
            case 4:
                depense.setQuantity(((Number) aValue).intValue());
                break;
            case 5:
                depense.setUnit_price(((Number) aValue).doubleValue());
                break;
            case 7:
                depense.setDescription((String) aValue);
                break;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        // "ID", "CATEGORY", "BANDE", "DATE DEPENSE", "QUANTITE", "PRIX UNITAIRE", "TOTAL", "DESCRIPTION"
        switch (columnIndex) {
            case 0:
                return Long.class;
            case 1:
                return Category.class;
            case 2:
                return Bande.class;
            case 3:
                return Date.class;
            case 4:
                return Integer.class;
            case 5:
                return Double.class;
            case 6:
                return Double.class;
            case 7:
                return String.class;
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return super.isCellEditable(rowIndex, columnIndex) && columnIndex != 6;
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
