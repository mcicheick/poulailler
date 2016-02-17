package ui;

import data.BandeTable;
import data.PaymentTable;
import data.TransactionTable;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by sissoko on 14/02/2016.
 */
public class ModelViewer extends JFrame implements ModelListener {

    protected JScrollPane mainPanel;
    protected ModelView currentView;
    protected JButton retour;
    protected JMenuBar menuBar;
    protected JMenu menuFile;
    protected JMenu menuEdit;

    private static class History {
        ModelView view;
        History prev;
    }

    History history;

    public ModelViewer() {
        this.history = new History();
        setLayout(new BorderLayout());
        this.mainPanel = new JScrollPane();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setBackground(Color.lightGray);
        currentView = new BandeView();
        currentView.addModelListener(this);
        history.view = currentView;
        setTitle("Les bandes");
        mainPanel.setViewportView(currentView);
        getContentPane().add(mainPanel);
        JToolBar toolBar =  new JToolBar();
        retour = new JButton("Retour");
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                History prev = history.prev;
                if(prev != null) {
                    mainPanel.setViewportView(prev.view);
                    currentView = prev.view;
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

        setMenuBar();
    }

    private void setMenuBar() {
        menuBar = new JMenuBar();
        menuFile = new JMenu("File");
        JMenuItem menuItemBack = new JMenuItem("Back");
        menuItemBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retour.doClick();
            }
        });
        menuItemBack.setHorizontalTextPosition(JButton.RIGHT);
        menuFile.add(menuItemBack);
        menuItemBack.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.META_DOWN_MASK));

        menuBar.add(menuFile);
        menuEdit = new JMenu("Edit");

        JMenuItem selectAll = new JMenuItem("Select All");
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.META_DOWN_MASK));
        menuEdit.add(selectAll);
        selectAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentView.table.selectAll();
            }
        });

        menuBar.add(menuEdit);
        setJMenuBar(menuBar);
    }

    private void addHistory(ModelView view) {
        History newHistory = new History();
        newHistory.prev = this.history;
        newHistory.view = view;
        currentView = view;
        this.history = newHistory;
        view.addModelListener(this);
    }

    @Override
    public void fireModel(Model model) {
        if(model.getId() == null) {
            return;
        }
        if(model instanceof Bande) {
            Bande bande = (Bande) model;
            setTitle("Transactions - " + bande.toString());
            addHistory(new TransactionView(new TransactionTable(bande.getTransactions())));
            mainPanel.setViewportView(history.view);
        } else if(model instanceof Transaction) {
            Transaction transaction = (Transaction) model;
            setTitle("Paiements - " + transaction.toString());
            addHistory(new PaymentView(new PaymentTable(transaction.getPayments())));
            mainPanel.setViewportView(history.view);
        } else if(model instanceof User) {
            User user = (User) model;
            setTitle("Bandes - " + user.toString());
            addHistory(new BandeView(new BandeTable(user.getBandes())));
            mainPanel.setViewportView(history.view);
        }  else if(model instanceof Client) {
            Client client = (Client) model;
            setTitle("Transactions - " + client.toString());
            addHistory(new TransactionView(new TransactionTable(client.getTransactions())));
            mainPanel.setViewportView(history.view);
        }
        history.view.dataBase.setModel(model);

    }

    public static void main(String[] args) {
        try {
            java.lang.System.setProperty("apple.laf.useScreenMenuBar", "true");
        } catch (Exception e) {
            // try the older menu bar property
            java.lang.System.setProperty("com.apple.macos.useScreenMenuBar", "true");
        }
        ModelViewer frame = new ModelViewer();
        frame.pack();
        frame.setBounds(200, 200, 940, 480);
        frame.setVisible(true);
    }
}
