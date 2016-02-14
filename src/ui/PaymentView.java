package ui;

import controllers.TransactionController;
import data.PaymentTable;
import models.Transaction;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

/**
 * Created by sissoko on 13/02/2016.
 */
public class PaymentView extends ModelView {

    public PaymentView() {
        this(new PaymentTable());
    }

    public PaymentView(PaymentTable dataBase) {
        super(dataBase);
        render();
    }

    private void render() {
        JComboBox<Transaction> comboBoxTransaction = new JComboBox<Transaction>();
        TableColumn colorColumnTransaction = table.getColumn("TRANSACTION");
        comboBoxTransaction.setModel(new DefaultComboBoxModel<Transaction>(new Vector<Transaction>(TransactionController.getInstance().select("o from Transaction o").getResultList())));
        colorColumnTransaction.setCellEditor(new DefaultCellEditor(comboBoxTransaction));

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Payment View");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setBackground(Color.lightGray);
        PaymentView mainPanel = new PaymentView();
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setBounds(200, 200, 640, 480);
        frame.setVisible(true);
    }
}
