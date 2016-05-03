package views;

import data.CategoryTable;
import models.Category;
import models.Model;
import views.forms.CategoryForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by sissoko on 13/02/2016.
 */
@SuppressWarnings("serial")
public class CategoryView extends ModelView {

    public CategoryView() {
        this(new CategoryTable());
    }

    public CategoryView(CategoryTable dataBase) {
        super(dataBase);
        render();
    }

    private void render() {
    	
    	setActionListener(newButton, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final PJDialog dialog = new PJDialog(getObserver());
                Category category = new Category();
                CategoryForm form = new CategoryForm(category);
                dialog.add(form);
                form.setViewer(CategoryView.this);
                dialog.setVisible(true);
                setActionListener(form.getSendButton(), new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Category binded = form.getCategory();
                        if(binded != null) {
                            binded.save();
                            dialog.dispose();
                            dataBase.addModel(binded);
                            dataBase.fireTableDataChanged();
                        }
                    }
                });

                setActionListener(form.getCancelButton(), new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                    }
                });
            }
        });
        
    }

    
    @Override
    public void fireModel(Model model) {
        final PJDialog dialog = new PJDialog(getObserver());
        Category category = (Category) model;
        dialog.setTitle(category.toString());
        CategoryForm form = new CategoryForm(category);
        dialog.add(form);
        dialog.setVisible(true);
        ModelView.setActionListener(form.getSendButton(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Category binded = form.getCategory();
                if(binded != null) {
                    binded.save();
                    dialog.dispose();
                }
            }
        });

        ModelView.setActionListener(form.getCancelButton(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Category View");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setBackground(Color.lightGray);
        CategoryView mainPanel = new CategoryView();
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setBounds(200, 200, 840, 480);
        frame.setVisible(true);
    }
}
