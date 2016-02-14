package ui;

import data.BandeTable;
import data.PaymentTable;
import data.TransactionTable;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by sissoko on 14/02/2016.
 */
public class ModelViewer extends JFrame implements ModelListener {

    protected JScrollPane mainPanel;
    protected BandeView bandeView;

    private static class History {
        ModelView view;
        History prev;
    }

    History history;

    public ModelViewer() {
        history = new History();
        setLayout(new BorderLayout());
        this.mainPanel = new JScrollPane();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setBackground(Color.lightGray);
        bandeView = new BandeView();
        bandeView.addModelListener(this);
        history.view = bandeView;
        mainPanel.setViewportView(bandeView);
        getContentPane().add(mainPanel);
        JToolBar toolBar =  new JToolBar();
        JButton retour = new JButton("Retour");
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                History prev = history.prev;
                if(prev != null) {
                    mainPanel.setViewportView(prev.view);
                    if(prev.view.dataBase.getModel() != null) {
                        setTitle(prev.view.dataBase.getModel().toString());
                    }
                    prev.view.dataBase.fireTableChanged(null);
                    history = prev;
                }
            }
        });
        toolBar.add(retour);
        JButton user = new JButton("Utilisateurs");
        user.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModelView view = new UserView();
                addHistory(view);
                mainPanel.setViewportView(history.view);
            }
        });
        toolBar.add(user);
        JButton client = new JButton("Clients");
        client.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModelView view = new ClientView();
                addHistory(view);
                mainPanel.setViewportView(history.view);
            }
        });
        toolBar.add(client);
        getContentPane().add(BorderLayout.NORTH, toolBar);
    }

    private void addHistory(ModelView view) {
        History newHistory = new History();
        newHistory.prev = this.history;
        newHistory.view = view;
        this.history = newHistory;
        view.addModelListener(this);
    }

    @Override
    public void fireModel(Model model) {
        if(model instanceof Bande) {
            Bande bande = (Bande) model;
            setTitle(bande.toString());
            addHistory(new TransactionView(new TransactionTable(bande.getTransactions())));
            mainPanel.setViewportView(history.view);
        } else if(model instanceof Transaction) {
            Transaction transaction = (Transaction) model;
            setTitle(transaction.toString());
            addHistory(new PaymentView(new PaymentTable(transaction.getPayments())));
            mainPanel.setViewportView(history.view);
        } else if(model instanceof User) {
            User user = (User) model;
            setTitle(user.toString());
            addHistory(new BandeView(new BandeTable(user.getBandes())));
            mainPanel.setViewportView(history.view);
        }  else if(model instanceof Client) {
            Client client = (Client) model;
            setTitle(client.toString());
            addHistory(new TransactionView(new TransactionTable(client.getTransactions())));
            mainPanel.setViewportView(history.view);
        }
        history.view.dataBase.setModel(model);

    }


    public static void main(String[] args) {
        ModelViewer frame = new ModelViewer();
        frame.pack();
        frame.setBounds(200, 200, 740, 480);
        frame.setVisible(true);
    }
}
