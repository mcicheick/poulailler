package ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * Created by sissoko on 14/02/2016.
 */
public class ModelViewerPanel extends JPanel implements LayoutManager {
    protected Dimension origin = new Dimension(0, 0);
    protected JScrollPane tableAggregate;

    public ModelViewerPanel() {
        tableAggregate = new JScrollPane();
        tableAggregate.setBorder(new BevelBorder(BevelBorder.LOWERED));
        add(tableAggregate);
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
        Rectangle b = parent.getBounds();
        int topHeight = 30;
        int inset = 4;
        tableAggregate.setBounds(new Rectangle(inset,
                inset + topHeight,
                b.width - 2 * inset,
                b.height - 2 * inset - topHeight));
    }
}
