package ui;

import controllers.UserController;
import models.Bande;
import models.User;

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

public class BandeForm extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 7513192410381964952L;
    public static final ResourceBundle bundle = ResourceBundle.getBundle("resources/Messages");
    protected Bande bande;

    protected JLabel userLabel;
    protected JComboBox<User> userField;
    protected JLabel userLabelError;
    protected InputGroup userInputGroup;

    protected JLabel arrived_dateLabel;
    protected JFormattedTextField arrived_dateField;
    protected JLabel arrived_dateLabelError;
    protected InputGroup arrived_dateInputGroup;

    protected JLabel initial_countLabel;
    protected JFormattedTextField initial_countField;
    protected JLabel initial_countLabelError;
    protected InputGroup initial_countInputGroup;
    

    protected JButton sendButton;

    public BandeForm() {
        this(new Bande());
    }

    /**
     * @param bande
     */
    public BandeForm(Bande bande) {
        this.bande = bande;
        init();
    }

    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.userLabel = new JLabel(bundle.getString("label.user"));
        this.userField = new JComboBox<User>();
        this.userLabelError = new JLabel();
        this.userInputGroup = new InputGroup(userLabel, userField, userLabelError);
        userInputGroup.validate(new Validator() {
            @Override
            public boolean test() {
                return userInputGroup.getValue() == null;
            }
        });
        add(userInputGroup);
        List<User> users = UserController.getInstance().select("b from User b").getResultList();
        this.userField.setModel(new DefaultComboBoxModel<User>(new Vector<User>(users)));
        this.userField.setSelectedItem(bande.getUser());

        this.arrived_dateLabel = new JLabel(bundle.getString("label.arrived_date"));
        this.arrived_dateField = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        this.arrived_dateLabelError = new JLabel();
        this.arrived_dateInputGroup = new InputGroup(arrived_dateLabel, arrived_dateField, arrived_dateLabelError);
        arrived_dateInputGroup.validate(new Validator() {
            @Override
            public boolean test() {
                return arrived_dateInputGroup.getValue() == null;
            }
        });
        add(arrived_dateInputGroup);

        this.initial_countLabel = new JLabel(bundle.getString("label.initial_count"));
        this.initial_countField = new JFormattedTextField(new DecimalFormat());
        this.initial_countLabelError = new JLabel();
        this.initial_countInputGroup = new InputGroup(initial_countLabel, initial_countField, initial_countLabelError);
        initial_countInputGroup.validate(new Validator() {
            @Override
            public boolean test() {
                return initial_countInputGroup.getValue() == null;
            }
        });
        add(initial_countInputGroup);

        sendButton = new JButton(bundle.getString("button.send"));
        add(sendButton);

        bind();

    }

    private void bind() {
        arrived_dateInputGroup.setKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (arrived_dateInputGroup.hasError()) {
                        arrived_dateInputGroup.setError(BandeForm.bundle.getString("error.arrived_date"));
                    } else {
                        arrived_dateInputGroup.setError("");
                        initial_countField.grabFocus();
                    }
                }
            }
        });

        initial_countInputGroup.setKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (initial_countInputGroup.hasError()) {
                        initial_countInputGroup.setError(BandeForm.bundle.getString("error.initial_count"));
                    } else {
                        initial_countInputGroup.setError("");
                        getRootPane().setDefaultButton(sendButton);
                    }
                }
            }
        });



        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Bande binded = getBande();
                if(binded != null) {
                    binded.save();
                }
            }
        });
    }

    public Bande getBande() {
        
        if (userInputGroup.hasError()) {
            userField.grabFocus();
            return null;
        }
        bande.setUser(((User) userInputGroup.getValue()));
        
        if (arrived_dateInputGroup.hasError()) {
            arrived_dateField.grabFocus();
            arrived_dateInputGroup.setError(BandeForm.bundle.getString("error.arrived_date"));
            return null;
        }
        bande.setArrived_date((Date) arrived_dateInputGroup.getValue());
        
        if (initial_countInputGroup.hasError()) {
            initial_countField.grabFocus();
            initial_countInputGroup.setError(BandeForm.bundle.getString("error.initial_count"));
            return null;
        }
        bande.setInitial_count(((Long) initial_countInputGroup.getValue()).intValue());

        return bande;
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        BandeForm userForm = new BandeForm();
        frame.getContentPane().add(userForm);
        frame.setBounds(200, 200, 500, 250);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
