package ui;

import controllers.ClientController;
import models.Payment;
import models.Transaction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * Created by sissoko on 11/02/2016.
 */

public class PaymentForm extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 7513192410381964952L;
    public static final ResourceBundle bundle = ResourceBundle.getBundle("resources/Messages");
    protected Payment payment;

    protected JLabel transactionLabel;
    protected JComboBox<Transaction> transactionField;
    protected JLabel transactionLabelError;
    protected InputGroup transactionInputGroup;

    protected JLabel payment_dateLabel;
    protected JFormattedTextField payment_dateField;
    protected JLabel payment_dateLabelError;
    protected InputGroup payment_dateInputGroup;

    protected JLabel amountLabel;
    protected JFormattedTextField amountField;
    protected JLabel amountLabelError;
    protected InputGroup amountInputGroup;


    protected JButton sendButton;

    public PaymentForm() {
        this(new Payment());
    }

    /**
     * @param payment
     */
    public PaymentForm(Payment payment) {
        this.payment = payment;
        init();
    }

    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.transactionLabel = new JLabel(bundle.getString("label.transaction"));
        this.transactionField = new JComboBox<Transaction>();
        this.transactionLabelError = new JLabel();
        this.transactionInputGroup = new InputGroup(transactionLabel, transactionField, transactionLabelError);
        transactionInputGroup.validate(new Validator() {
            @Override
            public boolean test() {
                return transactionInputGroup.getValue() == null;
            }
        });
        add(transactionInputGroup);
        List<Transaction> transactions = ClientController.getInstance().select("b from Transaction b").getResultList();
        this.transactionField.setModel(new DefaultComboBoxModel<Transaction>(new Vector<Transaction>(transactions)));
        this.transactionField.setSelectedItem(payment.getTransaction());

        this.payment_dateLabel = new JLabel(bundle.getString("label.payment_date"));
        this.payment_dateField = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        this.payment_dateLabelError = new JLabel();
        this.payment_dateInputGroup = new InputGroup(payment_dateLabel, payment_dateField, payment_dateLabelError);
        payment_dateInputGroup.validate(new Validator() {
            @Override
            public boolean test() {
                return payment_dateInputGroup.getValue() == null;
            }
        });
        add(payment_dateInputGroup);

        this.amountLabel = new JLabel(bundle.getString("label.amount"));
        this.amountField = new JFormattedTextField(new DecimalFormat("#.##"));
        this.amountLabelError = new JLabel();
        this.amountInputGroup = new InputGroup(amountLabel, amountField, amountLabelError);
        amountInputGroup.validate(new Validator() {
            @Override
            public boolean test() {
                return amountInputGroup.getValue() == null;
            }
        });
        add(amountInputGroup);



        sendButton = new JButton(bundle.getString("button.send"));
        add(sendButton);

        bind();

    }

    private void bind() {
        payment_dateInputGroup.setKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (payment_dateInputGroup.hasError()) {
                        payment_dateInputGroup.setError(PaymentForm.bundle.getString("error.payment_date"));
                    } else {
                        payment_dateInputGroup.setError("");
                        amountField.grabFocus();
                    }
                }
            }
        });

        amountInputGroup.setKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (amountInputGroup.hasError()) {
                        amountInputGroup.setError(PaymentForm.bundle.getString("error.amount"));
                    } else {
                        amountInputGroup.setError("");
                        getRootPane().setDefaultButton(sendButton);
                    }
                }
            }
        });



        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Payment binded = getPayment();
                if(binded != null) {
                    binded.save();
                }
            }
        });
    }

    public Payment getPayment() {

        if (transactionInputGroup.hasError()) {
            transactionField.grabFocus();
            return null;
        }
        payment.setTransaction((Transaction) transactionInputGroup.getValue());
        
        if (payment_dateInputGroup.hasError()) {
            payment_dateField.grabFocus();
            payment_dateInputGroup.setError(PaymentForm.bundle.getString("error.payment_date"));
            return null;
        }
        payment.setPayment_date((Date) payment_dateInputGroup.getValue());
        
        if (amountInputGroup.hasError()) {
            amountField.grabFocus();
            amountInputGroup.setError(PaymentForm.bundle.getString("error.amount"));
            return null;
        }
        payment.setAmount(((Number) amountInputGroup.getValue()).doubleValue());

        return payment;
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        PaymentForm clientForm = new PaymentForm();
        frame.getContentPane().add(clientForm);
        frame.setBounds(200, 200, 500, 250);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
