package ui;

import controllers.BandeController;
import controllers.ClientController;
import models.Bande;
import models.Client;
import models.Transaction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sissoko on 11/02/2016.
 */

public class TransactionForm extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 7513192410381964952L;
    public static final ResourceBundle bundle = ResourceBundle.getBundle("resources/Messages");
    protected Transaction transaction;

    protected JLabel transaction_dateLabel;
    protected JFormattedTextField transaction_dateField;
    protected JLabel transaction_dateLabelError;
    protected InputGroup transaction_dateInputGroup;

    protected JLabel unit_priceLabel;
    protected JFormattedTextField unit_priceField;
    protected JLabel unit_priceLabelError;
    protected InputGroup unit_priceInputGroup;

    protected JLabel price_by_kiloLabel;
    protected JFormattedTextField price_by_kiloField;
    protected JLabel price_by_kiloLabelError;
    protected InputGroup price_by_kiloInputGroup;

    protected JLabel quantityLabel;
    protected JFormattedTextField quantityField;
    protected JLabel quantityLabelError;
    protected InputGroup quantityInputGroup;

    protected JLabel weightLabel;
    protected JFormattedTextField weightField;
    protected JLabel weightLabelError;
    protected InputGroup weightInputGroup;

    protected JLabel bandeLabel;
    protected JComboBox<Bande> bandeField;
    protected JLabel bandeLabelError;
    protected InputGroup bandeInputGroup;

    protected JLabel clientLabel;
    protected JComboBox<Client> clientField;
    protected JLabel clientLabelError;
    protected InputGroup clientInputGroup;

    protected JButton sendButton;

    public TransactionForm() {
        this(new Transaction());
    }

    /**
     * @param transaction
     */
    public TransactionForm(Transaction transaction) {
        this.transaction = transaction;
        init();
    }

    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.bandeLabel = new JLabel(bundle.getString("label.bande"));
        this.bandeField = new JComboBox<Bande>();
        this.bandeLabelError = new JLabel();
        this.bandeInputGroup = new InputGroup(bandeLabel, bandeField, bandeLabelError);
        bandeInputGroup.validate(new Validator() {
            @Override
            public boolean test() {
                return bandeInputGroup.getValue() == null;
            }
        });
        add(bandeInputGroup);
        List<Bande> bandes = BandeController.getInstance().select("b from Bande b").getResultList();
        this.bandeField.setModel(new DefaultComboBoxModel<Bande>(new Vector<Bande>(bandes)));

        this.bandeField.setSelectedItem(transaction.getBande());

        this.clientLabel = new JLabel(bundle.getString("label.client"));
        this.clientField = new JComboBox<Client>();
        this.clientLabelError = new JLabel();
        this.clientInputGroup = new InputGroup(clientLabel, clientField, clientLabelError);
        clientInputGroup.validate(new Validator() {
            @Override
            public boolean test() {
                return clientInputGroup.getValue() == null;
            }
        });
        add(clientInputGroup);
        List<Client> clients = ClientController.getInstance().select("b from Client b").getResultList();
        this.clientField.setModel(new DefaultComboBoxModel<Client>(new Vector<Client>(clients)));

        this.clientField.setSelectedItem(transaction.getClient());

        this.transaction_dateLabel = new JLabel(bundle.getString("label.transaction_date"));
        this.transaction_dateField = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        this.transaction_dateLabelError = new JLabel();
        this.transaction_dateInputGroup = new InputGroup(transaction_dateLabel, transaction_dateField, transaction_dateLabelError);
        transaction_dateInputGroup.validate(new Validator() {
            @Override
            public boolean test() {
                return transaction_dateInputGroup.getValue() == null;
            }
        });
        add(transaction_dateInputGroup);
        this.transaction_dateField.setValue(transaction.getTransaction_date());

        this.unit_priceLabel = new JLabel(bundle.getString("label.unit_price"));
        this.unit_priceField = new JFormattedTextField(new DecimalFormat("#.##"));
        this.unit_priceLabelError = new JLabel();
        this.unit_priceInputGroup = new InputGroup(unit_priceLabel, unit_priceField, unit_priceLabelError);
        unit_priceInputGroup.validate(new Validator() {
            @Override
            public boolean test() {
                return unit_priceInputGroup.getValue() == null;
            }
        });
        add(unit_priceInputGroup);
        this.unit_priceField.setValue(transaction.getUnit_price());

        this.price_by_kiloLabel = new JLabel(bundle.getString("label.price_by_kilo"));
        this.price_by_kiloField = new JFormattedTextField(new DecimalFormat("#.##"));
        this.price_by_kiloLabelError = new JLabel();
        this.price_by_kiloInputGroup = new InputGroup(price_by_kiloLabel, price_by_kiloField, price_by_kiloLabelError);
        price_by_kiloInputGroup.validate(new Validator() {
            @Override
            public boolean test() {
                return price_by_kiloInputGroup.getValue() == null;
            }
        });
        add(price_by_kiloInputGroup);
        this.price_by_kiloField.setValue(transaction.getPrice_by_kilo());

        this.quantityLabel = new JLabel(bundle.getString("label.quantity"));
        this.quantityField = new JFormattedTextField(new DecimalFormat("#.##"));
        this.quantityLabelError = new JLabel();
        this.quantityInputGroup = new InputGroup(quantityLabel, quantityField, quantityLabelError);
        quantityInputGroup.validate(new Validator() {
            @Override
            public boolean test() {
                return quantityInputGroup.getValue() == null;
            }
        });
        add(quantityInputGroup);
        this.quantityField.setValue(transaction.getQuantity());

        this.weightLabel = new JLabel(bundle.getString("label.weight"));
        this.weightField = new JFormattedTextField(new DecimalFormat("#.##"));
        this.weightLabelError = new JLabel();
        this.weightInputGroup = new InputGroup(weightLabel, weightField, weightLabelError);
        weightInputGroup.validate(new Validator() {
            @Override
            public boolean test() {
                return weightInputGroup.getValue() == null;
            }
        });
        add(weightInputGroup);
        this.weightField.setValue(transaction.getWeight());

        sendButton = new JButton(bundle.getString("button.send"));
        add(sendButton);

        bind();

    }

    private void bind() {
        transaction_dateInputGroup.setKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (transaction_dateInputGroup.hasError()) {
                        transaction_dateInputGroup.setError(TransactionForm.bundle.getString("error.transaction_date"));
                    } else {
                        transaction_dateInputGroup.setError("");
                        unit_priceField.grabFocus();
                    }
                }
            }
        });

        unit_priceInputGroup.setKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (unit_priceInputGroup.hasError()) {
                        unit_priceInputGroup.setError(TransactionForm.bundle.getString("error.unit_price"));
                    } else {
                        unit_priceInputGroup.setError("");
                        price_by_kiloField.grabFocus();
                    }
                }
            }
        });

        price_by_kiloInputGroup.setKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (price_by_kiloInputGroup.hasError()) {
                        price_by_kiloInputGroup.setError(TransactionForm.bundle.getString("error.price_by_kilo"));
                    } else {
                        price_by_kiloInputGroup.setError("");
                        quantityField.grabFocus();
                    }
                }
            }
        });
        
        quantityInputGroup.setKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (quantityInputGroup.hasError()) {
                        quantityInputGroup.setError(TransactionForm.bundle.getString("error.quantity"));
                    } else {
                        quantityInputGroup.setError("");
                        weightField.grabFocus();
                    }
                }
            }
        });
        
        weightInputGroup.setKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (weightInputGroup.hasError()) {
                        weightInputGroup.setError(TransactionForm.bundle.getString("error.weight"));
                    } else {
                        weightInputGroup.setError("");
                        getRootPane().setDefaultButton(sendButton);
                    }
                }
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Transaction binded = getTransaction();
                if(binded != null) {
                    binded.save();
                }
            }
        });
    }

    public Transaction getTransaction() {
        if (bandeInputGroup.hasError()) {
            bandeField.grabFocus();
            return null;
        }
        transaction.setBande(((Bande) bandeInputGroup.getValue()));

        if (clientInputGroup.hasError()) {
            clientField.grabFocus();
            return null;
        }
        transaction.setClient(((Client) clientInputGroup.getValue()));
        
        if (transaction_dateInputGroup.hasError()) {
            transaction_dateField.grabFocus();
            transaction_dateInputGroup.setError(TransactionForm.bundle.getString("error.transaction_date"));
            return null;
        }
        transaction.setTransaction_date((Date) transaction_dateInputGroup.getValue());
        
        if (unit_priceInputGroup.hasError()) {
            unit_priceField.grabFocus();
            unit_priceInputGroup.setError(TransactionForm.bundle.getString("error.unit_price"));
            return null;
        }
        transaction.setUnit_price(((Number) unit_priceInputGroup.getValue()).doubleValue());

        if (price_by_kiloInputGroup.hasError()) {
            price_by_kiloField.grabFocus();
            price_by_kiloInputGroup.setError(TransactionForm.bundle.getString("error.price_by_kilo"));
            return null;
        }
        transaction.setPrice_by_kilo(((Number) price_by_kiloInputGroup.getValue()).doubleValue());
        
        if (quantityInputGroup.hasError()) {
            quantityField.grabFocus();
            quantityInputGroup.setError(TransactionForm.bundle.getString("error.quantity"));
            return null;
        }
        transaction.setQuantity(((Number) quantityInputGroup.getValue()).doubleValue());
        
        if (weightInputGroup.hasError()) {
            weightField.grabFocus();
            weightInputGroup.setError(TransactionForm.bundle.getString("error.weight"));
            return null;
        }
        transaction.setWeight(((Number) weightInputGroup.getValue()).doubleValue());

        return transaction;
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        TransactionForm userForm = new TransactionForm();
        frame.getContentPane().add(userForm);
        frame.setBounds(200, 200, 500, 350);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        System.out.println(decimalFormat.format(2.9829));
    }

}
