package data;

import java.util.List;

import models.Category;
import controllers.CategoryController;

/**
 * Created by sissoko on 13/02/2016.
 */
public class CategoryTable extends ModelTable {

	public CategoryTable() {
		this(CategoryController.getInstance().select("b from Category b")
				.getResultList());
	}

	public CategoryTable(List models) {
		super(models);
		this.columnNames = new String[] { "ID", "TITLE" };
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Category category = (Category) models.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return category.getId();
		case 1:
			return category.getTitle();
		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Category category = (Category) models.get(rowIndex);
		switch (columnIndex) {
		case 1:
			category.setTitle((String) aValue);
			break;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Long.class;
		case 1:
			return String.class;
		}
		return super.getColumnClass(columnIndex);
	}

	/**
	 *
	 * @param rowIndex
	 * @return
	 */
	public Category getRow(int rowIndex) {
		return (Category) super.getRow(rowIndex);
	}

	public void addRow() {
		Category category = new Category();
		models.add(category);
	}

	@Override
	public void update() {
		this.models = CategoryController.getInstance()
				.select("b from Category b").getResultList();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return super.isCellEditable(rowIndex, columnIndex);
	}
}
