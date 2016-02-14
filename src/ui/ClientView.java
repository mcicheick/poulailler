package ui;

import data.ClientTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by sissoko on 13/02/2016.
 */
public class ClientView extends ModelView {

    public ClientView() {
        super(new ClientTable());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Client View");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setBackground(Color.lightGray);
        ClientView mainPanel = new ClientView();
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setBounds(200, 200, 640, 480);
        frame.setVisible(true);
    }
}
