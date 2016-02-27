package views;

import controllers.BandeController;
import data.DeathTable;
import models.Bande;
import models.Death;
import models.Model;
import views.forms.DeathForm;

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
public class DeathView extends ModelView {

    public DeathView() {
        this(new DeathTable());
    }

    public DeathView(DeathTable dataBase) {
        super(dataBase);
        setActionListener(newButton, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final PJDialog dialog = new PJDialog(getObserver());
                Death death = new Death();
                death.setBande((Bande)getModel());
                DeathForm form = new DeathForm(death);
                dialog.add(form);
                dialog.setVisible(true);
                setActionListener(form.getSendButton(), new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Death binded = form.getDeath();
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
        if(observer != null) {
            final PJDialog dialog = new PJDialog(observer);
            Death death = (Death) model;
            dialog.setTitle(death.toString());
            DeathForm form = new DeathForm(death);
            dialog.add(form);
            dialog.setVisible(true);
            ModelView.setActionListener(form.getSendButton(), new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Death binded = form.getDeath();
                    if (binded != null) {
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
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Death View");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setBackground(Color.lightGray);
        DeathView mainPanel = new DeathView();
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setBounds(200, 200, 840, 480);
        frame.setVisible(true);
    }
}
