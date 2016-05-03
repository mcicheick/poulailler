package views;

import data.RememberTable;

import javax.swing.*;
import javax.swing.table.TableColumn;

import controllers.BandeController;
import views.forms.RememberForm;
import views.forms.RememberForm;
import models.Bande;
import models.Client;
import models.Model;
import models.Remember;
import models.Remember;

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
public class RememberView extends ModelView {

    public RememberView() {
        this(new RememberTable());
    }

    public RememberView(RememberTable dataBase) {
        super(dataBase);
        setActionListener(newButton, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final PJDialog dialog = new PJDialog(getObserver());
                Remember transaction = new Remember();
                Model model = getModel();
                if(model instanceof Bande) {
                    transaction.setBande((Bande) model);
                }
                RememberForm form = new RememberForm(transaction);
                dialog.add(form);
                dialog.setVisible(true);
                setActionListener(form.getSendButton(), new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Remember binded = form.getRemember();
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
    }

    @Override
    public void fireModel(Model model) {
        final PJDialog dialog = new PJDialog(getObserver());
        Remember remember = (Remember) model;
        dialog.setTitle(remember.toString());
        RememberForm form = new RememberForm(remember);
        dialog.add(form);
        dialog.setVisible(true);
        ModelView.setActionListener(form.getSendButton(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Remember binded = form.getRemember();
                if(binded != null) {
                    binded.save();
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
    

    public static void main(String[] args) {
        JFrame frame = new JFrame("Remember View");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setBackground(Color.lightGray);
        RememberView mainPanel = new RememberView();
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setBounds(200, 200, 840, 480);
        frame.setVisible(true);
    }
}
