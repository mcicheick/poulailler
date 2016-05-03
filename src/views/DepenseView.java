package views;

import controllers.BandeController;
import controllers.CategoryController;
import data.DepenseTable;
import models.*;
import views.forms.DepenseForm;

import javax.swing.*;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

/**
 * Created by sissoko on 13/02/2016.
 */
public class DepenseView extends ModelView {

	public DepenseView() {
		this(new DepenseTable());
	}

	public DepenseView(DepenseTable dataBase) {
		super(dataBase);
		setActionListener(newButton, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final PJDialog dialog = new PJDialog(getObserver());
				Depense depense = new Depense();
				depense.setBande((Bande) getModel());
				DepenseForm form = new DepenseForm(depense);
				dialog.add(form);
				dialog.setVisible(true);
				setActionListener(form.getSendButton(), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Depense binded = form.getDepense();
						if (binded != null) {
							binded.save();
							dialog.dispose();
							dataBase.addModel(binded);
							dataBase.fireTableDataChanged();
						}
					}
				});

				setActionListener(form.getCancelButton(), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dialog.dispose();
					}
				});
			}
		});
		render();
	}

	private void render() {
		JComboBox<Bande> comboBoxBande = new JComboBox<Bande>();
		TableColumn colorColumnBande = table.getColumn("BANDE");
		comboBoxBande.setModel(new DefaultComboBoxModel<Bande>(
				new Vector<Bande>(BandeController.getInstance()
						.select("o from Bande o").getResultList())));
		colorColumnBande.setCellEditor(new DefaultCellEditor(comboBoxBande));

		JComboBox<Category> comboBoxCategory = new JComboBox<Category>();
		TableColumn colorColumnCategory = table.getColumn("CATEGORY");
		comboBoxCategory.setModel(new DefaultComboBoxModel<Category>(
				new Vector<Category>(CategoryController.getInstance()
						.select("o from Category o").getResultList())));
		colorColumnCategory.setCellEditor(new DefaultCellEditor(
				comboBoxCategory));

	}

	@Override
	public void fireModel(Model model) {
		final PJDialog dialog = new PJDialog(getObserver());
		Depense depense = (Depense) model;
		dialog.setTitle(depense.toString());
		DepenseForm form = new DepenseForm(depense);
		dialog.add(form);
		dialog.setVisible(true);
		ModelView.setActionListener(form.getSendButton(), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Depense binded = form.getDepense();
				if (binded != null) {
					binded.save();
					dialog.dispose();
				}
			}
		});

		ModelView.setActionListener(form.getCancelButton(),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dialog.dispose();
					}
				});
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Depense View");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setBackground(Color.lightGray);
		DepenseView mainPanel = new DepenseView();
		frame.getContentPane().add(mainPanel);
		frame.pack();
		frame.setBounds(200, 200, 640, 480);
		frame.setVisible(true);
	}
}
