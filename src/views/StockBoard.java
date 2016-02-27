package views;

import models.Bande;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sissoko on 21/02/2016.
 */
public class StockBoard extends Board {

    protected JLabel soldLabel;
    protected JLabel costLabel;
    protected JLabel spentLabel;
    protected JLabel deathLabel;

    public StockBoard() {
        super("L'information de la bande");
        setLayout(this);
        init();

    }

    private void init() {
        titleLabel.setForeground(Color.WHITE);

        soldLabel = new JLabel();
        soldLabel.setForeground(Color.white);
        soldLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        add(soldLabel);

        costLabel = new JLabel();
        costLabel.setForeground(Color.white);
        costLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        add(costLabel);

        spentLabel = new JLabel();
        spentLabel.setForeground(Color.white);
        spentLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        add(spentLabel);

        deathLabel = new JLabel();
        deathLabel.setForeground(Color.white);
        deathLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        add(deathLabel);
    }


    @Override
    public void layoutContainer(Container parent) {
        super.layoutContainer(parent);
        Rectangle bound = parent.getBounds();
        int insetY = 4;
        int width = bound.width;
        int inset = (int)(width * 0.1);
        int height = bound.height;
        int paddingTop = (int)((height - 2 * insetY) * .20);
        int elementWidth = (int)((width - 2 * inset));
        soldLabel.setBounds(inset, insetY + paddingTop, elementWidth, 30);
        costLabel.setBounds(inset, insetY + 2 * paddingTop, elementWidth, 30);
        deathLabel.setBounds(inset, insetY + 3 * paddingTop, elementWidth, 30);
        spentLabel.setBounds(inset, insetY + 4 * paddingTop, elementWidth, 30);
    }

    @Override
    public void update(Object model) {
        Bande bande = (Bande) model;
        if(bande != null) {
            if(bande.getBenefit() > 0) {
                soldLabel.setForeground(new Color(10, 200, 10));
            }
            soldLabel.setText(String.format( "Vendu          : %.2f", bande.getSold()));
            costLabel.setText(String.format( "Nombre initial : %d", bande.getInitial_count()));
            deathLabel.setText(String.format("Nombre de mort : %d", bande.getDeath()));
            spentLabel.setText(String.format("Nombre restant : %d", bande.getRemain_count()));
        }
    }
}
