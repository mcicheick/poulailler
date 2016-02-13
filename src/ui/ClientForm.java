package ui;

import models.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

/**
 * Created by sissoko on 11/02/2016.
 */

public class ClientForm extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 7513192410381964952L;
    static final String PHONE_REGEX = "\\+?[0-9]{8,11}";
    public static final ResourceBundle bundle = ResourceBundle.getBundle("resources/Messages");
    protected Client client;

    protected JLabel firstNameLabel;
    protected JTextField firstNameField;
    protected JLabel firstNameLabelError;
    protected InputGroup firstNameInputGroup;

    protected JLabel lastNameLabel;
    protected JTextField lastNameField;
    protected JLabel lastNameLabelError;
    protected InputGroup lastNameInputGroup;

    protected JLabel phoneLabel;
    protected JTextField phoneField;
    protected JLabel phoneLabelError;
    protected InputGroup phoneInputGroup;

    protected JTextField addressField;
    protected JLabel addressLabelError;
    protected JLabel addressLabel;
    protected InputGroup addressInputGroup;

    protected JButton sendButton;

    public ClientForm() {
        this(new Client());
    }

    /**
     * @param client
     */
    public ClientForm(Client client) {
        this.client = client;
        init();
    }

    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.firstNameLabel = new JLabel(bundle.getString("label.first_name"));
        this.firstNameField = new JTextField();
        this.firstNameLabelError = new JLabel();
        this.firstNameInputGroup = new InputGroup(firstNameLabel, firstNameField, firstNameLabelError);
        firstNameInputGroup.validate(new Validator() {
            @Override
            public boolean test() {
                String text = (String) firstNameInputGroup.getValue();
                return (text == null || text.trim().isEmpty());
            }
        });
        add(firstNameInputGroup);

        this.lastNameLabel = new JLabel(bundle.getString("label.last_name"));
        this.lastNameField = new JTextField();
        this.lastNameLabelError = new JLabel();
        this.lastNameInputGroup = new InputGroup(lastNameLabel, lastNameField, lastNameLabelError);
        lastNameInputGroup.validate(new Validator() {
            @Override
            public boolean test() {
                String text = (String) lastNameInputGroup.getValue();
                return (text == null || text.trim().isEmpty());
            }
        });
        add(lastNameInputGroup);

        this.phoneLabel = new JLabel(bundle.getString("label.phone"));
        this.phoneField = new JTextField();
        this.phoneLabelError = new JLabel();
        this.phoneInputGroup = new InputGroup(phoneLabel, phoneField, phoneLabelError);
        phoneInputGroup.validate(new Validator() {
            @Override
            public boolean test() {
                String text = (String) phoneInputGroup.getValue();
                return (text == null || text.trim().isEmpty()) || !text.matches(PHONE_REGEX);
            }
        });
        add(phoneInputGroup);

        this.addressLabel = new JLabel(bundle.getString("label.address"));
        this.addressField = new JTextField();
        this.addressLabelError = new JLabel();
        this.addressInputGroup = new InputGroup(addressLabel, addressField, addressLabelError);
        add(addressInputGroup);

        sendButton = new JButton(bundle.getString("button.send"));
        add(sendButton);

        bind();

    }

    private void bind() {
        firstNameInputGroup.setKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (firstNameInputGroup.hasError()) {
                        firstNameInputGroup.setError(ClientForm.bundle.getString("error.first_name"));
                    } else {
                        firstNameInputGroup.setError("");
                        lastNameField.grabFocus();
                    }
                }
            }
        });

        lastNameInputGroup.setKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (lastNameInputGroup.hasError()) {
                        lastNameInputGroup.setError(ClientForm.bundle.getString("error.last_name"));
                    } else {
                        lastNameInputGroup.setError("");
                        phoneField.grabFocus();
                    }
                }
            }
        });

        phoneInputGroup.setKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (phoneInputGroup.hasError()) {
                        phoneInputGroup.setError(ClientForm.bundle.getString("error.phone"));
                    } else {
                        phoneInputGroup.setError("");
                        addressField.grabFocus();
                    }
                }
            }
        });

        addressInputGroup.setKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (addressInputGroup.hasError()) {
                        addressInputGroup.setError(ClientForm.bundle.getString("error.address"));
                    } else {
                        addressInputGroup.setError("");
                        getRootPane().setDefaultButton(sendButton);
                    }
                }
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client binded = getClient();
                if(binded != null) {
                    binded.save();
                }
            }
        });
    }

    public Client getClient() {
        if (firstNameInputGroup.hasError()) {
            firstNameField.grabFocus();
            firstNameInputGroup.setError(ClientForm.bundle.getString("error.first_name"));
            return null;
        }
        client.setFirst_name((String) firstNameInputGroup.getValue());
        
        if (lastNameInputGroup.hasError()) {
            lastNameField.grabFocus();
            lastNameInputGroup.setError(ClientForm.bundle.getString("error.last_name"));
            return null;
        }
        client.setLast_name((String) lastNameInputGroup.getValue());

        if (phoneInputGroup.hasError()) {
            phoneField.grabFocus();
            phoneInputGroup.setError(ClientForm.bundle.getString("error.phone"));
            return null;
        }
        client.setPhone((String) phoneInputGroup.getValue());

        if (addressInputGroup.hasError()) {
            addressField.grabFocus();
            addressInputGroup.setError(ClientForm.bundle.getString("error.address"));
            return null;
        }
        client.setAddress((String) addressInputGroup.getValue());

        return client;
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        ClientForm userForm = new ClientForm();
        frame.getContentPane().add(userForm);
        frame.setBounds(200, 200, 500, 250);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
