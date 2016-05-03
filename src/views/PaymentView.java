package views;

import controllers.TransactionController;
import data.PaymentTable;
import models.Model;
import models.Payment;
import models.Transaction;
import views.forms.PaymentForm;

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
@SuppressWarnings("serial")
public class PaymentView extends ModelView {

    public PaymentView() {
        this(new PaymentTable());
    }

    public PaymentView(PaymentTable dataBase) {
        super(dataBase);
        setActionListener(newButton, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final PJDialog dialog = new PJDialog(getObserver());
                Payment payment = new Payment();
                dialog.setTitle(payment.toString());
                payment.setTransaction((Transaction)getModel());
                PaymentForm form = new PaymentForm(payment);
                dialog.add(form);
                dialog.setVisible(true);
                setActionListener(form.getSendButton(), new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Payment binded = form.getPayment();
                        if(binded != null) {
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

    @Override
    public void fireModel(Model model) {
        final PJDialog dialog = new PJDialog(getObserver());
        Payment payment = (Payment) model;
        dialog.setTitle(payment.toString());
        PaymentForm form = new PaymentForm(payment);
        dialog.add(form);
        dialog.setVisible(true);
        ModelView.setActionListener(form.getSendButton(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Payment binded = form.getPayment();
                if(binded != null) {
                    //binded.save();
                    dialog.dispose();
                }
            }
        });

        ModelView.setActionListener(form.getCancelButton(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
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
        mainPanel.setObserver(frame);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setBounds(200, 200, 640, 480);
        frame.setVisible(true);
    }
}
