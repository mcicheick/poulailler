/**
 *
 */
package views.forms;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyListener;

/**
 * @author sissoko
 */
public class InputGroup extends JPanel implements InputGroupListener, LayoutManager {
    protected Dimension origin = new Dimension(0, 0);
    protected Dimension preferred = origin;
    protected Dimension minimum = origin;
    /**
     *
     */
    private static final long serialVersionUID = 4989507959985704286L;
    private JComponent component;
    JScrollPane scrollPane;

    private JLabel label;

    private JLabel labelError;

    public boolean hasError;

    public String lastError;

    Validator validator;

    public InputGroup(JLabel label, JComponent component, JLabel labelError) {
        this.label = label;
        label.setBackground(Color.blue);
        this.component = component;
        this.labelError = labelError;
        this.labelError.setBackground(Color.red);
        labelError.setFont(new Font("Lucida Grande", Font.BOLD, 14));
        labelError.setForeground(Color.RED);
        this.hasError = false;
        setLayout(this);
        add(label);
        if(component instanceof JTextArea) {
            scrollPane = new JScrollPane(component);
            add(scrollPane);
        } else {
            add(component);
        }
        add(this.labelError);
        validate(new Validator() {
            @Override
            public boolean test() {
                return getValue() == null;
            }
        });
    }

    public InputGroup() {
        this(new JLabel(), new JFormattedTextField(), new JLabel());
    }

    public void setComponent(JFormattedTextField component) {
        this.component = component;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public void setLabelError(JLabel labelError) {
        this.labelError = labelError;
    }

    @Override
    public Object getValue() {
        if (component instanceof JFormattedTextField) {
            return ((JFormattedTextField) component).getValue();
        }
        if (component instanceof JComboBox) {
            return ((JComboBox<?>) component).getSelectedItem();
        }
        return ((JTextComponent) component).getText();
    }

    @Override
    public void setError(String error) {
        if (!("".equals(error) || error == null)) {
            hasError = true;
            component.setForeground(Color.red);
        } else {
            hasError = false;
            component.setForeground(Color.black);
        }
        lastError = error;
        labelError.setText(error);
    }

    @Override
    public void setKeyListener(KeyListener l) {
        component.addKeyListener(l);
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

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return new Dimension(500, 250);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(300, 250);
    }

    @Override
    public void layoutContainer(Container parent) {
        Rectangle bound = parent.getBounds();
        int inset = 4;
        int x = bound.x;
        int y = bound.y;
        int width = bound.width;
        //int height = bound.height;
        int labelWidth = (int)(width * 0.2);
        int inputWidth = (int)(width * 0.8);
        int labelHeight = 30;
        label.setBounds(0, 0, labelWidth, labelHeight);
        if(scrollPane != null) {
            JTextArea textArea = (JTextArea) component;
            scrollPane.setBounds(labelWidth, 0, inputWidth - inset, 2 * labelHeight);
            textArea.setRows(3);
            labelError.setBounds(labelWidth + inset, 5 * labelHeight, inputWidth - inset, labelHeight);
        } else {
            component.setBounds(labelWidth, 0, inputWidth - inset, labelHeight);
            labelError.setBounds(labelWidth + inset, labelHeight, inputWidth - inset, labelHeight);
        }

    }
}
