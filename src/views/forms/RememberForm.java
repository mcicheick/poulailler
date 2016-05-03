package views.forms;

import controllers.BandeController;
import models.Bande;
import models.Remember;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * Created by sissoko on 07/03/2016 09:01:05.
 */
public class RememberForm extends BaseForm implements LayoutManager {
    
    protected JLabel bandeLabel;
    protected JComboBox<Bande> bandeField;
    protected JLabel bandeLabelError;
    protected InputGroup bandeInputGroup;

    protected JLabel remember_dateLabel;
    protected JFormattedTextField remember_dateField;
    protected JLabel remember_dateLabelError;
    protected InputGroup remember_dateInputGroup;
    

    private Remember remember;

    public RememberForm() {
        this(new Remember());
    }

    /**
     *
     * @param remember
     */
    public RememberForm(Remember remember) {
        super();
        this.remember = remember;
        setLayout(this);
        init();
        bind();
    }

    private void init() {
        
        this.bandeLabel = new JLabel(bundle.getString("label.bande"));
        this.bandeField = new JComboBox<Bande>();
        this.bandeLabelError = new JLabel();
        this.bandeInputGroup = new InputGroup(bandeLabel, bandeField, bandeLabelError);
        add(bandeInputGroup);
        java.util.List<Bande> bandes = BandeController.getInstance().select("b from Bande b").getResultList();
        this.bandeField.setModel(new DefaultComboBoxModel<Bande>(new Vector<Bande>(bandes)));
        this.bandeField.setSelectedItem(remember.getBande());

        this.remember_dateLabel = new JLabel("Date remember");
        this.remember_dateField = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        this.remember_dateLabelError = new JLabel();
        this.remember_dateInputGroup = new InputGroup(remember_dateLabel, remember_dateField, remember_dateLabelError);
        add(remember_dateInputGroup);
        this.remember_dateField.setValue(remember.getRemember_date());
        

        add(sendButton);
        add(cancelButton);
    }

    private void bind() {

        remember_dateInputGroup.setKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (remember_dateInputGroup.hasError()) {
                        remember_dateInputGroup.setError("La date est incorrecte");
                    } else {
                        remember_dateInputGroup.setError("");
                    }
                }
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Remember binded = getRemember();
                if(binded != null) {
                    binded.save();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

    @Override
    public void layoutContainer(Container parent) {
        Rectangle bound = parent.getBounds();
        int inset = 4;
        int x = bound.x;
        int y = bound.y;
        int width = bound.width;
        int height = bound.height;
        int buttonWidth = 120;
        int inputGroupHeight = 60;
        
        bandeInputGroup.setBounds(inset, 0, width - inset, inputGroupHeight);
        remember_dateInputGroup.setBounds(inset, inputGroupHeight, width - inset, inputGroupHeight);
        

        sendButton.setBounds(x + width - buttonWidth - 2 * inset, y + height - 45, buttonWidth, 30);
        cancelButton.setBounds(x + width - (2 * buttonWidth + 2 * inset), y + height - 45, buttonWidth, 30);
    }

    public Remember getRemember() {
        
        if (bandeInputGroup.hasError()) {
            bandeField.grabFocus();
            bandeInputGroup.setError("Cette valeur est requise.");
            return null;
        }
        bandeInputGroup.setError("");
        remember.setBande(((Bande) bandeInputGroup.getValue()));
        
        if (remember_dateInputGroup.hasError()) {
            remember_dateField.grabFocus();
            remember_dateInputGroup.setError("La date est incorrecte.");
            return null;
        }
        remember_dateInputGroup.setError("");
        remember.setRemember_date((Date) remember_dateInputGroup.getValue());
        

        return remember;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Remember View");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setBackground(Color.lightGray);
        RememberForm mainPanel = new RememberForm();
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setBounds(200, 200, 640, 480);
        frame.setVisible(true);
    }
}
