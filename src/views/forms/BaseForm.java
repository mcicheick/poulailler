package views.forms;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * Created by sissoko on 20/02/2016.
 */
public abstract class BaseForm extends JPanel implements LayoutManager {
    public static final ResourceBundle bundle = ResourceBundle.getBundle("resources/Messages");
    protected Dimension origin = new Dimension(0, 0);
    protected JButton sendButton;
    protected JButton cancelButton;

    public BaseForm() {
        sendButton = new JButton("Send");
        cancelButton = new JButton("Cancel");
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

    public JButton getSendButton() {
        return sendButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}
