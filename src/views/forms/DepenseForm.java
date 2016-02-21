package views.forms;

import controllers.BandeController;
import models.Bande;
import models.Depense;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * Created by sissoko on 20/02/2016.
 */
public class DepenseForm extends BaseForm implements LayoutManager {

    protected JLabel bandeLabel;
    protected JComboBox<Bande> bandeField;
    protected JLabel bandeLabelError;
    protected InputGroup bandeInputGroup;

    protected JLabel depense_dateLabel;
    protected JFormattedTextField depense_dateField;
    protected JLabel depense_dateLabelError;
    protected InputGroup depense_dateInputGroup;

    protected JLabel amountLabel;
    protected JFormattedTextField amountField;
    protected JLabel amountLabelError;
    protected InputGroup amountInputGroup;

    protected JLabel descriptionLabel;
    protected JTextArea descriptionField;
    protected JLabel descriptionLabelError;
    protected InputGroup descriptionInputGroup;

    private Depense depense;

    public DepenseForm() {
        this(new Depense());
    }

    /**
     *
     * @param depense
     */
    public DepenseForm(Depense depense) {
        super();
        this.depense = depense;
        init();
        bind();
    }

    private void init() {
        setLayout(this);

        this.bandeLabel = new JLabel(bundle.getString("label.bande"));
        this.bandeField = new JComboBox<Bande>();
        this.bandeLabelError = new JLabel();
        this.bandeInputGroup = new InputGroup(bandeLabel, bandeField, bandeLabelError);
        add(bandeInputGroup);
        java.util.List<Bande> bandes = BandeController.getInstance().select("b from Bande b").getResultList();
        this.bandeField.setModel(new DefaultComboBoxModel<Bande>(new Vector<Bande>(bandes)));
        this.bandeField.setSelectedItem(depense.getBande());

        this.depense_dateLabel = new JLabel("Date depense");
        this.depense_dateField = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        this.depense_dateLabelError = new JLabel();
        this.depense_dateInputGroup = new InputGroup(depense_dateLabel, depense_dateField, depense_dateLabelError);
        add(depense_dateInputGroup);
        this.depense_dateField.setValue(depense.getDepense_date());

        this.amountLabel = new JLabel(bundle.getString("label.amount"));
        this.amountField = new JFormattedTextField(new DecimalFormat("#.##"));
        this.amountLabelError = new JLabel();
        this.amountInputGroup = new InputGroup(amountLabel, amountField, amountLabelError);
        add(amountInputGroup);
        this.amountField.setValue(depense.getAmount());

        this.descriptionLabel = new JLabel("Description");
        this.descriptionField = new JTextArea();
        this.descriptionField.setRows(5);
        this.descriptionLabelError = new JLabel();
        this.descriptionInputGroup = new InputGroup(descriptionLabel, descriptionField, descriptionLabelError);
        add(descriptionInputGroup);
        this.descriptionField.setText(depense.getDescription());
        add(sendButton);
        add(cancelButton);
    }

    private void bind() {
        depense_dateInputGroup.setKeyListener(new KeyAdapter() {
            
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (depense_dateInputGroup.hasError()) {
                        depense_dateInputGroup.setError("La date est incorrecte");
                    } else {
                        depense_dateInputGroup.setError("");
                        amountField.grabFocus();
                    }
                }
            }
        });

        amountInputGroup.setKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (amountInputGroup.hasError()) {
                        amountInputGroup.setError("La valeur est incorrecte");
                    } else {
                        amountInputGroup.setError("");
                        descriptionField.grabFocus();
                    }
                }
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Depense binded = getDepense();
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
        depense_dateInputGroup.setBounds(inset, inputGroupHeight, width - inset, inputGroupHeight);
        amountInputGroup.setBounds(inset, 2 * inputGroupHeight, width - inset, inputGroupHeight);
        descriptionInputGroup.setBounds(inset, 3 * inputGroupHeight, width - inset, inputGroupHeight);

        sendButton.setBounds(x + width - buttonWidth - 2 * inset, y + height - 45, buttonWidth, 30);
        cancelButton.setBounds(x + width - (2 * buttonWidth + 2 * inset), y + height - 45, buttonWidth, 30);
    }

    public Depense getDepense() {
        if (bandeInputGroup.hasError()) {
            bandeField.grabFocus();
            bandeInputGroup.setError("Cette valeur est requise.");
            return null;
        }
        bandeInputGroup.setError("");
        depense.setBande(((Bande) bandeInputGroup.getValue()));

        if (depense_dateInputGroup.hasError()) {
            depense_dateField.grabFocus();
            depense_dateInputGroup.setError("La date est incorrecte.");
            return null;
        }
        depense_dateInputGroup.setError("");
        depense.setDepense_date((Date) depense_dateInputGroup.getValue());

        if (amountInputGroup.hasError()) {
            amountField.grabFocus();
            amountInputGroup.setError("Le montant est incorrecte.");
            return null;
        }
        amountInputGroup.setError("");
        depense.setAmount(((Number) amountInputGroup.getValue()).doubleValue());

        depense.setDescription((String) descriptionInputGroup.getValue());

        return depense;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Payment View");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setBackground(Color.lightGray);
        DepenseForm mainPanel = new DepenseForm();
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setBounds(200, 200, 640, 480);
        frame.setVisible(true);
    }
}
