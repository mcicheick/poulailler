package views;

import controllers.BandeController;
import data.ObservationTable;
import models.Bande;
import models.Observation;
import views.forms.ObservationForm;

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
public class ObservationView extends ModelView {

    public ObservationView() {
        this(new ObservationTable());
    }

    public ObservationView(ObservationTable dataBase) {
        super(dataBase);
        setActionListener(newButton, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final PJDialog dialog = new PJDialog(getObserver());
                Observation observation = new Observation();
                observation.setBande((Bande)getModel());
                ObservationForm form = new ObservationForm(observation);
                dialog.add(form);
                dialog.setVisible(true);
                setActionListener(form.getSendButton(), new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Observation binded = form.getObservation();
                        if(binded != null) {
                            binded.save();
                            dialog.dispose();
                            dataBase.addModel(binded);
                            dataBase.fireTableDataChanged();
                        } else {
                            System.out.println("Coucou coucou coucou.");
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
