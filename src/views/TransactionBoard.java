package views;

import controllers.TransactionController;
import models.Bande;
import models.Transaction;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sissoko on 21/02/2016.
 */
public class TransactionBoard extends Board {

    Bande bande;
    JLabel countLabel;
    JLabel paidLabel;
    JLabel remainLabel;

    public TransactionBoard() {
        super("L'information sur les transactions");
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
            java.util.List<Transaction> transactions = TransactionController.getInstance()
                    .select("t from Transaction t where t.bande = ?1 and t.transaction_date < ?2", bande, model).getResultList();

            Double paid = 0.0;
            Double remain = 0.0;
            for (Transaction transaction : transactions) {
                paid += transaction.getPaid();
                remain += transaction.getRemainAmount();
            }

            countLabel.setText(String.format( "Transaction   : %d", transactions.size()));
            paidLabel.setText(String.format(  "Payé          : %.2f", paid));
            remainLabel.setText(String.format("Reste à payer : %.2f", remain));
        }
    }
}
