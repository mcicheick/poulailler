package data;

import controllers.BandeController;
import models.Bande;
import models.User;

import java.util.Date;
import java.util.List;

/**
 * Created by sissoko on 13/02/2016.
 */
public class BandeTable extends ModelTable {

    public BandeTable() {
        this(BandeController.getInstance().select("b from Bande b order by b.arrived_date desc").getResultList());
    }

    public BandeTable(List models) {
        super(models);
        this.columnNames = new String[]{"ID", "DATE ARRIVÉE", "PRICE", "NOMBRE INITIAL", "COÛT", "NOMBRE RESTANT", "USER", "VENDUS"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Bande bande = (Bande) models.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return bande.getId();
            case 1:
                return bande.getArrived_date();
            case 2:
                return bande.getPrice();
            case 3:
                return bande.getInitial_count();
            case 4:
                return bande.getCost();
            case 5:
                return bande.getRemain_count();
            case 6:
                return bande.getUser();
            case 7:
                return bande.getSold();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Bande bande = (Bande) models.get(rowIndex);
        switch (columnIndex) {
            case 1:
                bande.setArrived_date((Date) aValue);
                break;
            case 2:
                bande.setPrice((Double) aValue);
                break;
            case 3:
                bande.setInitial_count((Integer) aValue);
                if(bande.getId() == null) {
                    bande.setRemain_count((Integer) aValue);
                }
                break;
            case 5:
                bande.setRemain_count((Integer) aValue);
                break;
            case 6:
                bande.setUser((User) aValue);
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
            case 4:
                return Double.class;
            case 3:
            case 5:
                return Integer.class;
            case 6:
                return User.class;
        }
        return super.getColumnClass(columnIndex);
    }

    /**
     *
     * @param rowIndex
     * @return
     */
    public Bande getRow(int rowIndex) {
        return (Bande) super.getRow(rowIndex);
    }

    public void addRow() {
        Bande bande = new Bande();
        bande.setUser((User) model);
        models.add(bande);
    }

    @Override
    public void update() {
        this.models = BandeController.getInstance().select("b from Bande b order by b.arrived_date desc").getResultList();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return super.isCellEditable(rowIndex, columnIndex) && columnIndex != 5;
    }

    public Double totalSold() {
        Double sold = 0.0;
        for (int i = 0; i < models.size(); i++) {
            Bande bande = (Bande) models.get(i);
            sold += bande.getSold();
        }
        return sold;
    }
}
