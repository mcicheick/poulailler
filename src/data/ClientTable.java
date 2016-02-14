package data;

import controllers.ClientController;
import models.Client;

/**
 * Created by sissoko on 13/02/2016.
 */
public class ClientTable extends ModelTable {

    public ClientTable() {
        this.columnNames = new String[]{"ID", "PRÉNOM", "NOM", "TELEPHONE", "ADRESSE"};
        this.models = ClientController.getInstance().select("o from Client o order by o.first_name asc").getResultList();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Client client = (Client) models.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return client.getId();
            case 1:
                return client.getFirst_name();
            case 2:
                return client.getLast_name();
            case 3:
                return client.getPhone();
            case 4:
                return client.getAddress();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Client client = (Client) models.get(rowIndex);
        switch (columnIndex) {
            case 1:
                client.setFirst_name((String) aValue);
                break;
            case 2:
                client.setLast_name((String) aValue);
                break;
            case 3:
                client.setPhone((String) aValue);
                break;
            case 4:
                client.setAddress((String) aValue);
                break;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Long.class;
            case 1:
            case 2:
            case 3:
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
    public Client getRow(int rowIndex) {
        return (Client) super.getRow(rowIndex);
    }

    public void addRow() {
        models.add(new Client());
        fireTableDataChanged();
    }
}
