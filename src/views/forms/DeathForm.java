package views.forms;

import controllers.BandeController;
import models.Bande;
import models.Death;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * Created by sissoko on 23/02/2016 12:12:20.
 */
public class DeathForm extends BaseForm implements LayoutManager {

    protected JLabel bandeLabel;
    protected JComboBox<Bande> bandeField;
    protected JLabel bandeLabelError;
    protected InputGroup bandeInputGroup;

    protected JLabel death_dateLabel;
    protected JFormattedTextField death_dateField;
    protected JLabel death_dateLabelError;
    protected InputGroup death_dateInputGroup;

    protected JLabel death_countLabel;
    protected JFormattedTextField death_countField;
    protected JLabel death_countLabelError;
    protected InputGroup death_countInputGroup;


    private Death death;

    public DeathForm() {
        this(new Death());
    }

    /**
     *
     * @param death
     */
    public DeathForm(Death death) {
        super();
        this.death = death;
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
        this.bandeField.setSelectedItem(death.getBande());

        this.death_dateLabel = new JLabel("Date death");
        this.death_dateField = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"));
        this.death_dateLabelError = new JLabel();
        this.death_dateInputGroup = new InputGroup(death_dateLabel, death_dateField, death_dateLabelError);
        add(death_dateInputGroup);
        this.death_dateField.setValue(death.getDeath_date());

        this.death_countLabel = new JLabel("Nombre de mmort");
        this.death_countField = new JFormattedTextField(new DecimalFormat());
        this.death_countLabelError = new JLabel();
        this.death_countInputGroup = new InputGroup(death_countLabel, death_countField, death_countLabelError);
        add(death_countInputGroup);
        this.death_countField.setValue(death.getDeath_count());

        add(sendButton);
        add(cancelButton);
    }

    private void bind() {

        death_dateInputGroup.setKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (death_dateInputGroup.hasError()) {
                        death_dateInputGroup.setError("La date est incorrecte");
                    } else {
                        death_dateInputGroup.setError("");
                        death_countField.grabFocus();
                    }
                }
            }
        });
        
        death_countInputGroup.setKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (death_countInputGroup.hasError()) {
                        death_countInputGroup.setError("La valeur est incorrecte");
                    } else {
                        death_countInputGroup.setError("");
                        getRootPane().setDefaultButton(sendButton);
                    }
                }
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Death binded = getDeath();
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
        death_dateInputGroup.setBounds(inset, inputGroupHeight, width - inset, inputGroupHeight);
        death_countInputGroup.setBounds(inset, 2 * inputGroupHeight, width - inset, inputGroupHeight);

        sendButton.setBounds(x + width - buttonWidth - 2 * inset, y + height - 45, buttonWidth, 30);
        cancelButton.setBounds(x + width - (2 * buttonWidth + 2 * inset), y + height - 45, buttonWidth, 30);
    }

    public Death getDeath() {
        if (bandeInputGroup.hasError()) {
            bandeField.grabFocus();
            bandeInputGroup.setError("Cette valeur est requise.");
            return null;
        }
        bandeInputGroup.setError("");
        death.setBande(((Bande) bandeInputGroup.getValue()));

        if (death_countInputGroup.hasError()) {
            death_countField.grabFocus();
            death_countInputGroup.setError("Le nombre est incorrecte.");
            return null;
        }
        death_countInputGroup.setError("");
        Integer count = ((Number) death_countInputGroup.getValue()).intValue();
        if(count > death.getBande().getRemain_count()) {
            death_countField.grabFocus();
            death_countInputGroup.setError("Le nombre est incorrect. Il ne peut pas y avoir plus de mort que de poulet :)");
            return null;
        }
        death.setDeath_count(count);

        if (death_dateInputGroup.hasError()) {
            death_dateField.grabFocus();
            death_dateInputGroup.setError("La date est incorrecte.");
            return null;
        }
        death_dateInputGroup.setError("");
        death.setDeath_date((Date) death_dateInputGroup.getValue());

        return death;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Death View");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setBackground(Color.lightGray);
        DeathForm mainPanel = new DeathForm();
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setBounds(200, 200, 640, 480);
        frame.setVisible(true);
    }
}
