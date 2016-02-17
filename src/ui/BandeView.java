package ui;

import controllers.UserController;
import data.BandeTable;
import models.User;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

/**
 * Created by sissoko on 13/02/2016.
 */
public class BandeView extends ModelView {

    public BandeView() {
        this(new BandeTable());
    }

    public BandeView(BandeTable dataBase) {
        super(dataBase);
        render();
        JLabel soldLabel = new JLabel();
        header.add(soldLabel, 0);
        soldLabel.setText(String.format("Total vendu : %.2f", dataBase.totalSold()));
    }

    private void render() {
        JComboBox<User> comboBoxUser = new JComboBox<User>();
        TableColumn colorColumnUser = table.getColumn("USER");
        comboBoxUser.setModel(new DefaultComboBoxModel<User>(new Vector<User>(UserController.getInstance().select("o from User o").getResultList())));
        colorColumnUser.setCellEditor(new DefaultCellEditor(comboBoxUser));

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bande View");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setBackground(Color.lightGray);
        BandeView mainPanel = new BandeView();
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setBounds(200, 200, 840, 480);
        frame.setVisible(true);
    }
}
