package views;

import views.forms.BaseForm;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sissoko on 20/02/2016.
 */
public class PJDialog extends JDialog {
    public PJDialog(Frame owner) {
        super(owner);
        if(owner != null) {
            Rectangle b = owner.getBounds();
            setBounds(b.x + 30, b.y + 30, owner.getWidth() - 60, owner.getHeight() - 60);
        }
    }

    /**
     *
     * @param owner
     */
    public PJDialog(JDialog owner) {
        super(owner);
        if(owner != null) {
            Rectangle b = owner.getBounds();
            setBounds(b.x + 30, b.y + 30, owner.getWidth() - 60, owner.getHeight() - 60);
        }
    }

    @Override
    public Component add(Component comp) {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(comp);
        if(comp instanceof BaseForm) {
            BaseForm form = (BaseForm) comp;
            form.setOwner(this);
        }
        // add(scrollPane);
        return super.add(scrollPane);
    }
}
