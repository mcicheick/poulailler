package views;

import controllers.BandeController;
import controllers.ClientController;
import data.TransactionTable;
import models.Bande;
import models.Client;
import models.Model;
import models.Transaction;
import views.forms.TransactionForm;

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
public class TransactionView extends ModelView {

    public TransactionView() {
        this(new TransactionTable());
    }

    public TransactionView(TransactionTable dataBase) {
        super(dataBase);
        setActionListener(newButton, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final PJDialog dialog = new PJDialog(getObserver());
                Transaction transaction = new Transaction();
                Model model = getModel();
                if(model instanceof Bande) {
                    transaction.setBande((Bande) model);
                } else if (model instanceof Client) {
                    transaction.setClient((Client) model);
                }
                TransactionForm form = new TransactionForm(transaction);
                dialog.add(form);
                dialog.setVisible(true);
                setActionListener(form.getSendButton(), new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Transaction binded = form.getTransaction();
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
