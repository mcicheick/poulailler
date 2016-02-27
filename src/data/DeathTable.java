package data;

import controllers.DeathController;
import models.Bande;
import models.Death;

import java.util.Date;
import java.util.List;

/**
 * Created by sissoko on 13/02/2016.
 */
public class DeathTable extends ModelTable {

    public DeathTable() {
        this(DeathController.getInstance().select("b from Death b").getResultList());
    }

    public DeathTable(List models) {
        super(models);
        this.columnNames = new String[]{"ID", "DATE MORT", "NOMBRE", "BANDE"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Death death = (Death) models.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return death.getId();
            case 1:
                return death.getDeath_date();
            case 2:
                return death.getDeath_count();
            case 3:
                return death.getBande();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Death death = (Death) models.get(rowIndex);
        switch (columnIndex) {
            case 1:
                death.setDeath_date((Date) aValue);
                break;
            case 2:
                death.setDeath_count((Integer) aValue);
                break;
            case 3:
                death.setBande((Bande) aValue);
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
                return Integer.class;
            case 3:
                return Bande.class;
        }
        return super.getColumnClass(columnIndex);
    }

    /**
     *
     * @param rowIndex
     * @return
     */
    public Death getRow(int rowIndex) {
        return (Death) super.getRow(rowIndex);
    }

    public void addRow() {
        Death death = new Death();
        death.setBande((Bande) model);
        models.add(death);
    }

    @Override
    public void update() {
        this.models = DeathController.getInstance().select("b from Death b").getResultList();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return super.isCellEditable(rowIndex, columnIndex);
    }
}
