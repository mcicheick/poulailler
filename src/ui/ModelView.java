package ui;

import data.ModelTable;
import data.TableSorter;
import models.Model;

import javax.persistence.PersistenceException;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by sissoko on 13/02/2016.
 */
public abstract class ModelView extends JPanel implements LayoutManager {
    protected java.util.List<ModelListener> listenerList;
    protected Dimension origin = new Dimension(0, 0);
    protected TableSorter sorter;
    protected ModelTable dataBase;
    protected JScrollPane tableAggregate;

    protected JButton saveButton;
    protected JButton newButton;

    protected JTable table;

    public ModelView() {
        this(new ModelTable() {
            @Override
            public void addRow() {
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return null;
            }
        });
    }

    public ModelView(ModelTable dataBase) {
        listenerList = new ArrayList<ModelListener>();
        this.dataBase = dataBase;
        this.sorter = new TableSorter();
        sorter.setModel(dataBase);
        // Create the table
        table = new JTable(sorter);
        table.setGridColor(Color.blue);
        // Use a scrollbar, in case there are many columns.
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int selected = table.getSelectedRowCount();
                Model model = dataBase.getRow(selected);
                if (model == null) {
                    return;
                }
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        try {
                            saveButton.doClick();
                        } catch (PersistenceException pe) {
                            pe.printStackTrace();
                        }
                        break;
                    default:
                        // Do nothing.
                }
            }
        });

        // Install a mouse listener in the TableHeader as the sorter UI.
        sorter.addMouseListenerToHeaderInTable(table);

        tableAggregate = new JScrollPane(table);
        tableAggregate.setBorder(new BevelBorder(BevelBorder.LOWERED));
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] selecteds = table.getSelectedRows();
                for (int i = 0; i < selecteds.length; i++) {
                    int selected = selecteds[i];
                    Model model = dataBase.getRow(selected);
                    if (model != null) {
                        try {
                            model.save();
                        } catch (PersistenceException pe) {
                            pe.printStackTrace();
                        }
                    }
                }
                dataBase.fireTableDataChanged(); // Tell the listeners a new table has arrived.
            }
        });
        add(saveButton);

        newButton = new JButton("New");
        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dataBase.addRow();
                dataBase.fireTableDataChanged();
            }
        });
        add(newButton);
        add(tableAggregate);
        setLayout(this);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selected = table.getSelectedRow();
                Model model = dataBase.getRow(selected);
                if (e.getClickCount() == 2) {
                    fireModel(model);
                }
            }
        });

        tableAggregate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON3) {
                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenuItem newItem = new JMenuItem("New");
                    newItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            newButton.doClick();
                        }
                    });
                    popupMenu.add(newItem);
                    popupMenu.show(tableAggregate, e.getX(), e.getY());
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON3) {
                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenuItem newItem = new JMenuItem("Delete");
                    newItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int selects[] = table.getSelectedRows();
                            java.util.List<Model> toRemove = new ArrayList<>();
                            for (int i = 0; i < selects.length; i++) {
                                int selected = selects[i];
                                Model model = dataBase.getRow(selected);
                                if (model == null) {
                                    continue;
                                }
                                if (model.getId() != null) {
                                    model.delete();
                                }
                                toRemove.add(model);
                            }

                            for (int i = 0; i < toRemove.size(); i++) {
                                Model model = toRemove.get(i);
                                dataBase.removeRow(model);
                            }
                            dataBase.fireTableDataChanged();
                        }
                    });
                    int selects[] = table.getSelectedRows();
                    if (selects.length > 0) {
                        popupMenu.add(newItem);
                        popupMenu.show(table, e.getX(), e.getY());
                    }
                }
            }
        });

    }

    private void fireModel(Model model) {
        for (int i = 0; i < listenerList.size(); i++) {
            listenerList.get(i).fireModel(model);
        }
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
        newButton.setBounds(b.width - 2 * inset - 120, inset, 120, 25);
        saveButton.setBounds(b.width - 2 * inset - 245, inset, 120, 25);
        tableAggregate.setBounds(new Rectangle(inset,
                inset + topHeight,
                b.width - 2 * inset,
                b.height - 2 * inset - topHeight));
    }

    /**
     * @param l
     */
    public void addModelListener(ModelListener l) {
        this.listenerList.add(l);
    }

    /**
     * @param l
     */
    public void removeModelListener(ModelListener l) {
        this.listenerList.remove(l);
    }
}
