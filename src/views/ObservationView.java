package views;

import controllers.BandeController;
import data.ObservationTable;
import models.Bande;
import models.Model;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

/**
 * Created by sissoko on 13/02/2016.
 */
public class ObservationView extends ModelView {

    public ObservationView() {
        this(new ObservationTable());
    }

    public ObservationView(ObservationTable dataBase) {
        super(dataBase);
        render();
    }

    private void render() {
        JComboBox<Bande> comboBoxBande = new JComboBox<Bande>();
        TableColumn colorColumnBande = table.getColumn("BANDE");
        comboBoxBande.setModel(new DefaultComboBoxModel<Bande>(new Vector<Bande>(BandeController.getInstance().select("o from Bande o").getResultList())));
        colorColumnBande.setCellEditor(new DefaultCellEditor(comboBoxBande));
    }

    @Override
    public void fireModel(Model model) {
        // Do nothing
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Observation View");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setBackground(Color.lightGray);
        ObservationView mainPanel = new ObservationView();
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setBounds(200, 200, 840, 480);
        frame.setVisible(true);
    }

}
