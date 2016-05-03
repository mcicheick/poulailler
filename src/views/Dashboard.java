package views;

import controllers.BandeController;
import models.Bande;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * Created by sissoko on 18/02/2016.
 */
public class Dashboard extends ModelView implements LayoutManager {

    protected Dimension origin = new Dimension(0, 0);
    JComboBox<Bande> bandeBox;
    JFormattedTextField beginDate;
    JFormattedTextField endDate;
    StockBoard stockPanel;
    Board transactionPanel;
    Board depensePanel;

    public Dashboard() {
        setLayout(this);
        init();
        bind();
        add();
    }

    private void init() {
        this.bandeBox = new JComboBox<>();
        java.util.List<Bande> bandes = BandeController.getInstance().select("b from Bande b order by b.arrived_date desc").getResultList();
        this.bandeBox.setModel(new DefaultComboBoxModel<Bande>(new Vector<Bande>(bandes)));

        beginDate = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        beginDate.setValue(new Date());
        endDate = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        endDate.setValue(new Date());

        stockPanel = new StockBoard();
        stockPanel.setOpaque(true);
        stockPanel.setBackground(Color.WHITE);

        transactionPanel = new TransactionBoard();
        transactionPanel.setOpaque(true);
        transactionPanel.setBackground(Color.green);

        depensePanel = new DepenseBoard();
        depensePanel.setOpaque(true);
        depensePanel.setBackground(Color.red);

        update();
    }

    private void add() {
        add(bandeBox);
        add(beginDate);
        add(endDate);
        add(stockPanel);
        add(transactionPanel);
        add(depensePanel);
    }

    private void update() {
        Bande bande = bandeBox.getItemAt(bandeBox.getSelectedIndex());
        stockPanel.update(bande);
        transactionPanel.update(bande);
        transactionPanel.update(beginDate.getValue());
        depensePanel.update(bande);
        depensePanel.update(endDate.getValue());

    }

    private void bind() {

        bandeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               update();
            }
        });

        beginDate.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        beginDate.commitEdit();
                    } catch (ParseException e1) {}
                    transactionPanel.update(beginDate.getValue());
                }
            }

        });

        endDate.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        endDate.commitEdit();
                    } catch (ParseException e1) {}
                    depensePanel.update(endDate.getValue());
                }
            }

        });

        stockPanel.deathLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        stockPanel.deathLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	int selected = bandeBox.getSelectedIndex();
            	if(selected < 0) {
            		return;
            	}
                fireModel(bandeBox.getItemAt(selected));
                fireEvent("Deaths");
            }
        });

        transactionPanel.titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        transactionPanel.titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	int selected = bandeBox.getSelectedIndex();
            	if(selected < 0) {
            		return;
            	}
                fireModel(bandeBox.getItemAt(selected));
                fireEvent("Transactions");
            }
        });

        depensePanel.titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        depensePanel.titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	int selected = bandeBox.getSelectedIndex();
            	if(selected < 0) {
            		return;
            	}
                fireModel(bandeBox.getItemAt(selected));
                fireEvent("Depenses");
            }
        });
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return origin;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return origin;
    }

    @Override
    public void layoutContainer(Container parent) {
        Rectangle bound = parent.getBounds();
        int width = bound.width;
        int height = bound.height;
        int inset = (int)(width * 0.04);
        int insetY = 10;
        int elementWidth = (int)((width - 2 * inset) * 0.33);
        int padding = (int) (inset * 0.5);
        int bandeBoxHeight = 30;
        int boardHeight = (height - (4 * insetY + bandeBoxHeight));
        bandeBox.setBounds(padding, insetY, elementWidth, bandeBoxHeight);
        beginDate.setBounds(inset + elementWidth, insetY, elementWidth, bandeBoxHeight);
        endDate.setBounds(padding + inset + 2 * elementWidth, insetY, elementWidth, bandeBoxHeight);

        stockPanel.setBounds(padding, 2 * insetY + bandeBoxHeight, elementWidth, boardHeight);
        transactionPanel.setBounds(2 * padding + elementWidth, 2 * insetY + bandeBoxHeight, elementWidth, boardHeight);
        depensePanel.setBounds(3 * padding + 2 * elementWidth, 2 * insetY + bandeBoxHeight, elementWidth, boardHeight);
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame("Dashboard");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setBackground(Color.lightGray);
        Dashboard mainPanel = new Dashboard();
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setBounds(200, 200, 1000, 480);
        frame.setVisible(true);
    }
}
