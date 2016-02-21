package views;

import models.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Created by sissoko on 19/02/2016.
 */
public class ModelDetailView extends ModelView implements LayoutManager {

    protected Dimension origin = new Dimension(0, 0);
    protected JTabbedPane tabbedPane;

    private java.util.List<ModelView> viewList;

    public ModelDetailView() {
        tabbedPane = new JTabbedPane();
        add(tabbedPane);
        viewList = new ArrayList<>();
    }

    /**
     *
     * @param title
     * @param view
     */
    public void addModelView(String title, ModelView view) {
        tabbedPane.addTab(title, view);
        viewList.add(view);
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
        int topHeight = 0;
        int inset = 0;
        tabbedPane.setBounds(new Rectangle(inset,
                inset + topHeight,
                bound.width - 2 * inset,
                bound.height - 2 * inset - topHeight));
    }

    @Override
    public void addModelListener(ModelListener l) {
        super.addModelListener(l);
        for (int i = 0; i < viewList.size(); i++) {
            viewList.get(i).addModelListener(l);
        }
    }

    @Override
    public void setModel(Model model) {
        super.setModel(model);
        for (int i = 0; i < viewList.size(); i++) {
            viewList.get(i).setModel(model);
        }
    }

    @Override
    public void setObserver(JFrame observer) {
        super.setObserver(observer);
        for (int i = 0; i < viewList.size(); i++) {
            viewList.get(i).setObserver(observer);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ModelDetail View");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setBackground(Color.lightGray);
        ModelDetailView mainPanel = new ModelDetailView();
        mainPanel.addModelView("Bandes", new BandeView());
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setBounds(200, 200, 840, 480);
        frame.setVisible(true);
    }
}
