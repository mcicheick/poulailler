package views.forms;

import controllers.BandeController;
import models.Bande;
import models.Observation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

/**
 * Created by sissoko on 20/02/2016.
 */
public class ObservationForm extends BaseForm implements LayoutManager {

    protected JLabel bandeLabel;
    protected JComboBox<Bande> bandeField;
    protected JLabel bandeLabelError;
    protected InputGroup bandeInputGroup;

    protected JLabel descriptionLabel;
    protected JTextArea descriptionField;
    protected JLabel descriptionLabelError;
    protected InputGroup descriptionInputGroup;

    private Observation observation;

    public ObservationForm() {
        this(new Observation());
    }

    /**
     *
     * @param observation
     */
    public ObservationForm(Observation observation) {
        super();
        this.observation = observation;
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
        this.bandeField.setSelectedItem(observation.getBande());

        this.descriptionLabel = new JLabel("Description");
        this.descriptionField = new JTextArea();
        this.descriptionField.setRows(5);
        this.descriptionLabelError = new JLabel();
        this.descriptionInputGroup = new InputGroup(descriptionLabel, descriptionField, descriptionLabelError);
        add(descriptionInputGroup);
        this.descriptionField.setText(observation.getDescription());
        add(sendButton);
        add(cancelButton);
    }

    private void bind() {

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Observation binded = getObservation();
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
        descriptionInputGroup.setBounds(inset, inputGroupHeight, width - inset, inputGroupHeight);

        sendButton.setBounds(x + width - buttonWidth - 2 * inset, y + height - 45, buttonWidth, 30);
        cancelButton.setBounds(x + width - (2 * buttonWidth + 2 * inset), y + height - 45, buttonWidth, 30);
    }

    public Observation getObservation() {
        if (bandeInputGroup.hasError()) {
            bandeField.grabFocus();
            bandeInputGroup.setError("Cette valeur est requise.");
            return null;
        }
        bandeInputGroup.setError("");
        observation.setBande(((Bande) bandeInputGroup.getValue()));

        observation.setDescription((String) descriptionInputGroup.getValue());

        return observation;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Payment View");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setBackground(Color.lightGray);
        ObservationForm mainPanel = new ObservationForm();
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setBounds(200, 200, 640, 480);
        frame.setVisible(true);
    }
}
