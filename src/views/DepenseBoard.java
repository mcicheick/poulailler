package views;

import controllers.DepenseController;
import models.Bande;
import models.Depense;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sissoko on 21/02/2016.
 */
public class DepenseBoard extends Board {

    Bande bande;
    JLabel countLabel;
    JLabel paidLabel;
    JLabel remainLabel;

    public DepenseBoard() {
        super("L'information sur les dépenses");
        setLayout(this);
        titleLabel.setForeground(Color.BLACK);

        countLabel = new javax.swing.JLabel();
        countLabel.setForeground(Color.BLACK);
        countLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        add(countLabel);

        paidLabel = new javax.swing.JLabel();
        paidLabel.setForeground(Color.BLACK);
        paidLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        add(paidLabel);

        remainLabel = new javax.swing.JLabel();
        remainLabel.setForeground(Color.BLACK);
        remainLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        add(remainLabel);
    }

    @Override
    public void layoutContainer(Container parent) {
        super.layoutContainer(parent);
        Rectangle bound = parent.getBounds();
        int insetY = 4;
        int width = bound.width;
        int inset = (int) (width * 0.1);
        int height = bound.height;
        int paddingTop = (int) ((height - 2 * insetY) * .25);
        int elementWidth = (int) ((width - 2 * inset));
        countLabel.setBounds(inset, insetY + paddingTop, elementWidth, 30);
        paidLabel.setBounds(inset, insetY + 2 * paddingTop, elementWidth, 30);
        remainLabel.setBounds(inset, insetY + 3 * paddingTop, elementWidth, 30);
    }

    @Override
    public void update(Object model) {
        if (model instanceof Bande) {
            bande = (Bande) model;
        } else {
            if(bande == null) {
                return;
            }
            java.util.List<Depense> depenses = DepenseController.getInstance()
                    .select("t from Depense t where t.bande = ?1 and t.depense_date < ?2", bande, model).getResultList();

            Double paid = 0.0;
            Double remain = bande.getFixedCost();
            for (Depense depense : depenses) {
                paid += depense.getTotal();
            }

            countLabel.setText(String.format( "Depense       : %d", depenses.size()));
            paidLabel.setText(String.format(  "Total dépense : %.2f", paid));
            remainLabel.setText(String.format("Coût          : %.2f", remain));
        }
    }
}
