package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

/**
 * Created by sissoko on 21/02/2016.
 */
public abstract class Board extends JPanel implements LayoutManager {
    protected Dimension origin = new Dimension(0, 0);

    JLabel titleLabel;

    public Board(String title) {
        setLayout(this);
        titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.GRAY);
        titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        add(titleLabel);
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

    @Override
    public void layoutContainer(Container parent) {
        Rectangle bound = parent.getBounds();
        int insetY = 4;
        int width = bound.width;
        int inset = (int)(width * 0.1);
        int elementWidth = (int)((width - 2 * inset));
        titleLabel.setBounds(inset, insetY, elementWidth, 30);
    }

    /**
     *
     * @param model
     */
    public abstract void update(Object model);

    /**
     *
     * @param label
     * @param listener
     */
    public static void addMouseListener(JLabel label, MouseListener listener) {
        label.addMouseListener(listener);
    }
}
