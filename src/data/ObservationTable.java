package data;

import controllers.ObservationController;
import models.Bande;
import models.Observation;

import java.util.Date;
import java.util.List;

/**
 * Created by sissoko on 13/02/2016.
 */
public class ObservationTable extends ModelTable {

    public ObservationTable() {
        this(ObservationController.getInstance().select("b from Observation b").getResultList());
    }

    public ObservationTable(List models) {
        super(models);
        this.columnNames = new String[]{"ID", "DATE OBSERVATION", "DESCRIPTION", "BANDE"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Observation observation = (Observation) models.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return observation.getId();
            case 1:
                return observation.getObservation_date();
            case 2:
                return observation.getDescription();
            case 3:
                return observation.getBande();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Observation observation = (Observation) models.get(rowIndex);
        switch (columnIndex) {
            case 1:
                observation.setObservation_date((Date) aValue);
                break;
            case 2:
                observation.setDescription((String) aValue);
                break;
            case 3:
                observation.setBande((Bande) aValue);
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
                return String.class;
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
    public Observation getRow(int rowIndex) {
        return (Observation) super.getRow(rowIndex);
    }

    public void addRow() {
        Observation observation = new Observation();
        observation.setBande((Bande) model);
        models.add(observation);
    }

    @Override
    public void update() {
        this.models = ObservationController.getInstance().select("b from Observation b").getResultList();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return super.isCellEditable(rowIndex, columnIndex);
    }
}
