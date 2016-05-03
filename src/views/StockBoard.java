package views;

import models.Bande;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sissoko on 21/02/2016.
 */
public class StockBoard extends Board {

    protected JLabel soldLabel;
    protected JLabel beneficeLabel;
    protected JLabel costLabel;
    protected JLabel spentLabel;
    protected JLabel deathLabel;

    public StockBoard() {
        super("L'information de la bande");
        setLayout(this);
        init();

    }

    private void init() {
        titleLabel.setForeground(Color.BLACK);

        soldLabel = new JLabel();
        soldLabel.setForeground(Color.BLACK);
        soldLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        add(soldLabel);

        beneficeLabel = new JLabel();
        beneficeLabel.setForeground(Color.BLACK);
        beneficeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        add(beneficeLabel);

        costLabel = new JLabel();
        costLabel.setForeground(Color.BLACK);
        costLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        add(costLabel);

        spentLabel = new JLabel();
        spentLabel.setForeground(Color.BLACK);
        spentLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        add(spentLabel);

        deathLabel = new JLabel();
        deathLabel.setForeground(Color.BLACK);
        deathLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        add(deathLabel);
    }


    @Override
    public void layoutContainer(Container parent) {
        super.layoutContainer(parent);
        Rectangle bound = parent.getBounds();
        int insetY = 4;
        int width = bound.width;
        int inset = (int) (width * 0.1);
        int height = bound.height;
        int paddingTop = (int) ((height - 2 * insetY) * .18);
        int elementWidth = (int) ((width - 2 * inset));
        soldLabel.setBounds(inset, insetY + paddingTop, elementWidth, 30);
        beneficeLabel.setBounds(inset, insetY + 2 * paddingTop, elementWidth, 30);
        costLabel.setBounds(inset, insetY + 3 * paddingTop, elementWidth, 30);
        deathLabel.setBounds(inset, insetY + 4 * paddingTop, elementWidth, 30);
        spentLabel.setBounds(inset, insetY + 5 * paddingTop, elementWidth, 30);
    }

    @Override
    public void update(Object model) {
        Bande bande = (Bande) model;
        if (bande != null) {
            if (bande.getBenefit() > 0) {
                soldLabel.setForeground(new Color(10, 200, 10));
                beneficeLabel.setForeground(Color.BLUE);
            } else {
                beneficeLabel.setForeground(Color.RED);
            }
            soldLabel.setText(String.format("Vendu          : %.2f", bande.getSold()));
            costLabel.setText(String.format("Nombre initial : %d", bande.getInitial_count()));
            deathLabel.setText(String.format("Nombre de mort : %d", bande.getDeath()));
            spentLabel.setText(String.format("Nombre restant : %d", bande.getRemain_count()));
            beneficeLabel.setText(String.format("Benefice : %.2f", bande.getBenefit()));

        }
    }
}
