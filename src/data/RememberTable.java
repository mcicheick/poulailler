package data;

import controllers.RememberController;
import models.Bande;
import models.Remember;

import java.util.List;

/**
 * Created by sissoko on 13/02/2016.
 */
public class RememberTable extends ModelTable {

	public RememberTable() {
		this(RememberController.getInstance().select("b from Remember b")
				.getResultList());
	}

	public RememberTable(List models) {
		super(models);
		this.columnNames = new String[] { "ID", "BANDE" };
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Remember remember = (Remember) models.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return remember.getId();
		case 1:
			return remember.getBande();
		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Remember remember = (Remember) models.get(rowIndex);
		switch (columnIndex) {
		case 1:
			remember.setBande((Bande) aValue);
			break;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Long.class;
		case 1:
			return Bande.class;
		}
		return super.getColumnClass(columnIndex);
	}

	/**
	 *
	 * @param rowIndex
	 * @return
	 */
	public Remember getRow(int rowIndex) {
		return (Remember) super.getRow(rowIndex);
	}

	public void addRow() {
		Remember remember = new Remember();
		remember.setBande((Bande) model);
		models.add(remember);
	}

	@Override
	public void update() {
		this.models = RememberController.getInstance()
				.select("b from Remember b").getResultList();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return super.isCellEditable(rowIndex, columnIndex);
	}
}
