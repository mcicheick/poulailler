package views;

import controllers.BandeController;
import controllers.ClientController;
import data.TransactionTable;
import models.Bande;
import models.Client;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

/**
 * Created by sissoko on 13/02/2016.
 */
public class TransactionView extends ModelView {

    public TransactionView() {
        this(new TransactionTable());
    }

    public TransactionView(TransactionTable dataBase) {
        super(dataBase);
        render();
    }

    private void render() {
        JComboBox<Bande> comboBoxBande = new JComboBox<Bande>();
        TableColumn colorColumnBande = table.getColumn("BANDE");
        comboBoxBande.setModel(new DefaultComboBoxModel<Bande>(new Vector<Bande>(BandeController.getInstance().select("o from Bande o").getResultList())));
        colorColumnBande.setCellEditor(new DefaultCellEditor(comboBoxBande));

        JComboBox<Client> comboBoxClient = new JComboBox<Client>();
        TableColumn colorColumnClient = table.getColumn("CLIENT");
        comboBoxClient.setModel(new DefaultComboBoxModel<Client>(new Vector<Client>(ClientController.getInstance().select("o from Client o").getResultList())));
        colorColumnClient.setCellEditor(new DefaultCellEditor(comboBoxClient));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Transaction View");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setBackground(Color.lightGray);
        TransactionView mainPanel = new TransactionView();
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setBounds(200, 200, 640, 480);
        frame.setVisible(true);
    }
}
