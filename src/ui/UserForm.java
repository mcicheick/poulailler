package ui;

import controllers.UserController;
import models.User;
import tools.BCrypt;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

/**
 * Created by sissoko on 11/02/2016.
 */

public class UserForm extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 7513192410381964952L;
    static final String PASSWORD_REGEX = "[a-zA-Z0-9]{5,}";
    static String EMAIL_REGEX = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*"
            + "[\\w])?\\.)+[a-zA-Z0-9](?:[\\w-]*[\\w])?";
    public static final ResourceBundle userBundle = ResourceBundle.getBundle("resources/Messages");
    protected User user;

    protected JLabel firstNameLabel;
    protected JLabel lastNameLabel;
    protected JLabel emailLabel;
    protected JLabel passwordLabel;

    protected JTextField firstNameField;
    protected JTextField lastNameField;
    protected JTextField emailField;
    protected JPasswordField passwordField;

    protected JLabel firstNameLabelError;
    protected JLabel lastNameLabelError;
    protected JLabel emailLabelError;
    protected JLabel passwordLabelError;

    protected InputGroup firstNameInputGroup;
    protected InputGroup lastNameInputGroup;
    protected InputGroup emailInputGroup;
    protected InputGroup passwordInputGroup;

    protected JButton sendButton;

    public UserForm() {
        this(new User());
    }

    /**
     * @param user
     */
    public UserForm(User user) {
        this.user = user;
        init();
    }

    private void init() {
        this.firstNameLabel = new JLabel(userBundle.getString("label.first_name"));
        this.firstNameField = new JTextField();
        this.firstNameLabelError = new JLabel();

        this.lastNameLabel = new JLabel(userBundle.getString("label.last_name"));
        this.lastNameField = new JTextField();
        this.lastNameLabelError = new JLabel();

        this.emailLabel = new JLabel(userBundle.getString("label.email"));
        this.emailField = new JTextField();
        this.emailLabelError = new JLabel();

        this.passwordLabel = new JLabel(userBundle.getString("label.password"));
        this.passwordField = new JPasswordField();
        this.passwordField.enableInputMethods(false);
        this.passwordLabelError = new JLabel();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        firstNameInputGroup = new InputGroup(firstNameLabel, firstNameField, firstNameLabelError);
        add(firstNameInputGroup);
        firstNameInputGroup.validate(new Validator() {
            @Override
            public boolean test() {
                String text = (String) firstNameInputGroup.getValue();
                return (text == null || text.trim().isEmpty());
            }
        });

        lastNameInputGroup = new InputGroup(lastNameLabel, lastNameField, lastNameLabelError);
        add(lastNameInputGroup);
        lastNameInputGroup.validate(new Validator() {
            @Override
            public boolean test() {
                String text = (String) lastNameInputGroup.getValue();
                return (text == null || text.trim().isEmpty());
            }
        });

        emailInputGroup = new InputGroup(emailLabel, emailField, emailLabelError);
        add(emailInputGroup);
        emailInputGroup.validate(new Validator() {
            @Override
            public boolean test() {
                String text = (String) emailInputGroup.getValue();
                boolean check = (text == null || text.trim().isEmpty()) || !text.matches(EMAIL_REGEX);
                if(!check && user.getId() == null) {
                    User old = UserController.getInstance().findByEmail(text);
                    if(old != null) {
                        check = true;
                    }
                }
                return check;
            }
        });

        passwordInputGroup = new InputGroup(passwordLabel, passwordField, passwordLabelError);
        add(passwordInputGroup);
        passwordInputGroup.validate(new Validator() {
            @Override
            public boolean test() {
                String text = (String) passwordInputGroup.getValue();
                return (text == null || text.trim().isEmpty()) || !text.matches(PASSWORD_REGEX);
            }
        });

        sendButton = new JButton(userBundle.getString("button.send"));
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
                        firstNameInputGroup.setError(UserForm.userBundle.getString("error.first_name"));
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
                        lastNameInputGroup.setError(UserForm.userBundle.getString("error.last_name"));
                    } else {
                        lastNameInputGroup.setError("");
                        emailField.grabFocus();
                    }
                }
            }
        });

        emailInputGroup.setKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (emailInputGroup.hasError()) {
                        emailInputGroup.setError(UserForm.userBundle.getString("error.email"));
                    } else {
                        emailInputGroup.setError("");
                        passwordField.grabFocus();
                    }
                }
            }
        });

        passwordInputGroup.setKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (passwordInputGroup.hasError()) {
                        passwordInputGroup.setError(UserForm.userBundle.getString("error.password"));
                    } else {
                        passwordInputGroup.setError("");
                        getRootPane().setDefaultButton(sendButton);
                        sendButton.grabFocus();
                    }
                }
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User binded = getUser();
                if(binded != null) {
                    binded.save();
                }
            }
        });
    }

    public User getUser() {
        if (firstNameInputGroup.hasError()) {
            firstNameField.grabFocus();
            firstNameInputGroup.setError(UserForm.userBundle.getString("error.first_name"));
            return null;
        }
        user.setFirst_name((String) firstNameInputGroup.getValue());
        if (lastNameInputGroup.hasError()) {
            lastNameField.grabFocus();
            lastNameInputGroup.setError(UserForm.userBundle.getString("error.last_name"));
            return null;
        }
        user.setLast_name((String) lastNameInputGroup.getValue());
        if (emailInputGroup.hasError()) {
            emailField.grabFocus();
            emailInputGroup.setError(UserForm.userBundle.getString("error.email"));
            return null;
        }
        user.setEmail((String) emailInputGroup.getValue());
        if (passwordInputGroup.hasError()) {
            passwordField.grabFocus();
            passwordInputGroup.setError(UserForm.userBundle.getString("error.password"));
            return null;
        }
        String password = (String) passwordInputGroup.getValue();
        if(!password.equals(user.getPassword())) {
            password = BCrypt.hashpw(password, BCrypt.gensalt());
        }
        user.setPassword(password);
        return user;
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        UserForm userForm = new UserForm();
        frame.getContentPane().add(userForm);
        frame.setBounds(200, 200, 500, 250);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
