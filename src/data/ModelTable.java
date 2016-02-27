package data;

import models.Model;
import views.ModelView;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sissoko on 13/02/2016.
 */
public abstract class ModelTable extends AbstractTableModel {

    protected String[] columnNames = {"ID"};
    protected List<Model> models;

    protected boolean editable = false;

    protected Model model;

    protected ModelView owner;

    public ModelTable() {
        this(new ArrayList<>());
    }

    public ModelTable(List models) {
        this.models = models;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex > 0 && editable;
    }

    @Override
    public int getRowCount() {
        return models.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void update(){}

    @Override
    public void fireTableChanged(TableModelEvent e) {
        if(e == null && model == null) {
            update();
        }
        super.fireTableChanged(e);
    }

    @Override
    public void fireTableDataChanged() {
        if(owner != null) {
            owner.fireTableDataChanged();
        }
        super.fireTableDataChanged();
    }

    /**
     *
     * @param rowIndex
     * @return
     */
    public Model getRow(int rowIndex) {
        if(rowIndex < 0 || rowIndex >= getRowCount()) {
            return null;
        }
        return models.get(rowIndex);
    }

    abstract public void addRow();

    public void setModel(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    /**
     *
     * @param editable
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isEditable() {
        return editable;
    }

    public void removeRow(int selected) {
        if (selected < 0 || selected >= getRowCount()) {
            return;
        }
        models.remove(selected);
    }

    public void removeRow(Model model) {
        models.remove(model);
    }

    public void addModel(Model model) {
        models.add(model);
        fireTableDataChanged();
    }

    /**
     *
     * @param owner
     */
    public void setOwner(ModelView owner) {
        this.owner = owner;
    }
}
