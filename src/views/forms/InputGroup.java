/**
 *
 */
package views.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

/**
 * @author sissoko
 */
public class InputGroup extends JPanel implements InputGroupListener {

    /**
     *
     */
    private static final long serialVersionUID = 4989507959985704286L;
    private JComponent textField;

    private JLabel label;

    private JLabel labelError;

    public boolean hasError;

    public String lastError;

    Validator validator;

    public InputGroup(JLabel label, JComponent textField, JLabel labelError) {
        this.label = label;
        this.textField = textField;
        this.labelError = labelError;
        labelError.setFont(new Font("Lucida Grande", Font.BOLD, 14));
        labelError.setForeground(Color.RED);
        this.hasError = false;
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{100, 155, 0};
        gridBagLayout.rowHeights = new int[]{28, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.anchor = GridBagConstraints.WEST;
        gbc_label.insets = new Insets(0, 0, 5, 5);
        gbc_label.gridx = 0;
        gbc_label.gridy = 0;
        add(this.label, gbc_label);

        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.insets = new Insets(0, 0, 5, 0);
        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField.gridx = 1;
        gbc_textField.gridy = 0;
        add(this.textField, gbc_textField);

        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.anchor = GridBagConstraints.NORTH;
        gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblNewLabel.gridx = 1;
        gbc_lblNewLabel.gridy = 1;
        add(this.labelError, gbc_lblNewLabel);

    }

    public InputGroup() {
        this(new JLabel(), new JFormattedTextField(), new JLabel());
    }

    public void setTextField(JFormattedTextField textField) {
        this.textField = textField;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public void setLabelError(JLabel labelError) {
        this.labelError = labelError;
    }

    @Override
    public Object getValue() {
        if (textField instanceof JFormattedTextField) {
            return ((JFormattedTextField) textField).getValue();
        }
        if (textField instanceof JComboBox) {
            return ((JComboBox) textField).getSelectedItem();
        }
        return ((JTextField) textField).getText();
    }

    @Override
    public void setError(String error) {
        if (!("".equals(error) || error == null)) {
            hasError = true;
            textField.setForeground(Color.red);
        } else {
            hasError = false;
            textField.setForeground(Color.black);
        }
        lastError = error;
        labelError.setText(error);
    }

    @Override
    public void setKeyListener(KeyListener l) {
        textField.addKeyListener(l);
    }

    @Override
    public void validate(Validator validator) {
        this.validator = validator;
    }

    public boolean hasError() {
        if (validator != null) {
            hasError = validator.test();
            return hasError;
        }
        return false;
    }

}
