package data;

import controllers.UserController;
import models.User;

/**
 * Created by sissoko on 13/02/2016.
 */
public class UserTable extends ModelTable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2935073504699261122L;

	public UserTable() {
        this.columnNames = new String[]{"ID", "PRÉNOM", "NOM", "TELEPHONE"};
        this.models = UserController.getInstance().select("o from User o order by o.first_name asc").getResultList();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User client = (User) models.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return client.getId();
            case 1:
                return client.getFirst_name();
            case 2:
                return client.getLast_name();
            case 3:
                return client.getTelephone();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        User client = (User) models.get(rowIndex);
        switch (columnIndex) {
            case 1:
                client.setFirst_name((String) aValue);
                break;
            case 2:
                client.setLast_name((String) aValue);
                break;
            case 3:
                client.setTelephone((String) aValue);
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
    public User getRow(int rowIndex) {
        return (User) super.getRow(rowIndex);
    }

    public void addRow() {
        models.add(new User());
        fireTableDataChanged();
    }
}
