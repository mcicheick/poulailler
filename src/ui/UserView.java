package ui;

import data.UserTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by sissoko on 13/02/2016.
 */
public class UserView extends ModelView {

    public UserView() {
        super(new UserTable());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("User View");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setBackground(Color.lightGray);
        UserView mainPanel = new UserView();
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setBounds(200, 200, 640, 480);
        frame.setVisible(true);
    }
}
