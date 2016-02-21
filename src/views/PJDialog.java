package views;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sissoko on 20/02/2016.
 */
public class PJDialog extends JDialog {
    public PJDialog(Frame owner) {
        super(owner);
        Rectangle b = owner.getBounds();
        setBounds(b.x + 30, b.y + 30, owner.getWidth() - 60, owner.getHeight() - 60);
    }
}
