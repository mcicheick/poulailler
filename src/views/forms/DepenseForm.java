package views.forms;

import controllers.BandeController;
import controllers.CategoryController;
import models.Bande;
import models.Category;
import models.Depense;
import views.PJDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import static views.ModelView.setActionListener;

/**
 * Created by sissoko on 20/02/2016.
 */
public class DepenseForm extends BaseForm implements LayoutManager {

    protected JLabel bandeLabel;
    protected JComboBox<Bande> bandeField;
    protected JLabel bandeLabelError;
    protected InputGroup bandeInputGroup;

    protected JLabel categoryLabel;
    protected JComboBox<Category> categoryField;
    protected JLabel categoryLabelError;
    protected InputGroup categoryInputGroup;

    protected JLabel depense_dateLabel;
    protected JFormattedTextField depense_dateField;
    protected JLabel depense_dateLabelError;
    protected InputGroup depense_dateInputGroup;

    protected JLabel unit_priceLabel;
    protected JFormattedTextField unit_priceField;
    protected JLabel unit_priceLabelError;
    protected InputGroup unit_priceInputGroup;

    protected JLabel quantityLabel;
    protected JFormattedTextField quantityField;
    protected JLabel quantityLabelError;
    protected InputGroup quantityInputGroup;

    protected JLabel descriptionLabel;
    protected JTextArea descriptionField;
    protected JLabel descriptionLabelError;
    protected InputGroup descriptionInputGroup;

    private Depense depense;

    private Category category;

    public DepenseForm() {
        this(new Depense());
    }

    /**
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
        category = new Category();
        category.setTitle("Ajouter une catégorie");

        this.categoryLabel = new JLabel("Categorie");
        this.categoryField = new JComboBox<Category>();
        this.categoryLabelError = new JLabel();
        this.categoryInputGroup = new InputGroup(categoryLabel, categoryField, categoryLabelError);
        add(categoryInputGroup);
        java.util.List<Category> categorys = CategoryController.getInstance().select("b from Category b").getResultList();
        this.categoryField.setModel(new DefaultComboBoxModel<Category>(new Vector<Category>(categorys)));
        this.categoryField.setSelectedItem(depense.getCategory());
        this.categoryField.addItem(category);

        this.depense_dateLabel = new JLabel("Date depense");
        this.depense_dateField = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        this.depense_dateLabelError = new JLabel();
        this.depense_dateInputGroup = new InputGroup(depense_dateLabel, depense_dateField, depense_dateLabelError);
        add(depense_dateInputGroup);
        this.depense_dateField.setValue(depense.getDepense_date());

        this.unit_priceLabel = new JLabel("Prix unitaire");
        this.unit_priceField = new JFormattedTextField(new DecimalFormat("#.##"));
        this.unit_priceLabelError = new JLabel();
        this.unit_priceInputGroup = new InputGroup(unit_priceLabel, unit_priceField, unit_priceLabelError);
        add(unit_priceInputGroup);
        this.unit_priceField.setValue(depense.getUnit_price());

        this.quantityLabel = new JLabel("Quantité");
        this.quantityField = new JFormattedTextField(new DecimalFormat());
        this.quantityLabelError = new JLabel();
        this.quantityInputGroup = new InputGroup(quantityLabel, quantityField, quantityLabelError);
        add(quantityInputGroup);
        this.quantityField.setValue(depense.getQuantity());

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

        categoryField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected = categoryField.getSelectedIndex();
                if (selected != -1) {
                    Category cat = categoryField.getItemAt(selected);
                    if (cat != null) {
                        if (cat.equals(DepenseForm.this.category)) {
                            final PJDialog dialog = new PJDialog(DepenseForm.this.getOwner());
                            Category category = new Category();
                            CategoryForm form = new CategoryForm(category);
                            dialog.add(form);
                            dialog.setVisible(true);
                            setActionListener(form.getSendButton(), new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    Category binded = form.getCategory();
                                    //System.out.println(binded);
                                    if (binded != null) {
                                        binded.save();
                                        dialog.dispose();
                                        update(selected);
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
                    }
                }
            }
        });

        depense_dateInputGroup.setKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (depense_dateInputGroup.hasError()) {
                        depense_dateInputGroup.setError("La date est incorrecte");
                    } else {
                        depense_dateInputGroup.setError("");
                        unit_priceField.grabFocus();
                    }
                }
            }
        });

        unit_priceInputGroup.setKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (unit_priceInputGroup.hasError()) {
                        unit_priceInputGroup.setError("La valeur est incorrecte");
                    } else {
                        unit_priceInputGroup.setError("");
                        quantityInputGroup.grabFocus();
                    }
                }
            }
        });

        quantityInputGroup.setKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (quantityInputGroup.hasError()) {
                        quantityInputGroup.setError("La valeur est incorrecte");
                    } else {
                        quantityInputGroup.setError("");
                        descriptionField.grabFocus();
                    }
                }
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Depense binded = getDepense();
                if (binded != null) {
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

    public void update(int selected) {
        java.util.List<Category> categorys = CategoryController.getInstance().select("b from Category b").getResultList();
        category = new Category();
        category.setTitle("Ajouter une catégorie");
        categorys.add(category);
        categoryField.setModel(new DefaultComboBoxModel<Category>(new Vector<Category>(categorys)));
        categoryField.setSelectedIndex(selected);
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
        categoryInputGroup.setBounds(inset, 1 * inputGroupHeight, width - inset, inputGroupHeight);
        depense_dateInputGroup.setBounds(inset, 2 * inputGroupHeight, width - inset, inputGroupHeight);
        unit_priceInputGroup.setBounds(inset, 3 * inputGroupHeight, width - inset, inputGroupHeight);
        quantityInputGroup.setBounds(inset, 4 * inputGroupHeight, width - inset, inputGroupHeight);
        descriptionInputGroup.setBounds(inset, 5 * inputGroupHeight, width - inset, inputGroupHeight);

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

        if (categoryInputGroup.hasError()) {
            categoryField.grabFocus();
            categoryInputGroup.setError("Cette valeur est requise.");
            return null;
        }
        categoryInputGroup.setError("");
        depense.setCategory(((Category) categoryInputGroup.getValue()));

        if (depense_dateInputGroup.hasError()) {
            depense_dateField.grabFocus();
            depense_dateInputGroup.setError("La date est incorrecte.");
            return null;
        }
        depense_dateInputGroup.setError("");
        depense.setDepense_date((Date) depense_dateInputGroup.getValue());

        if (unit_priceInputGroup.hasError()) {
            unit_priceField.grabFocus();
            unit_priceInputGroup.setError("Le prix unitaire est incorrect.");
            return null;
        }
        unit_priceInputGroup.setError("");
        depense.setUnit_price(((Number) unit_priceInputGroup.getValue()).doubleValue());

        if (quantityInputGroup.hasError()) {
            quantityField.grabFocus();
            quantityInputGroup.setError("La quantité est incorrecte.");
            return null;
        }
        quantityInputGroup.setError("");
        depense.setQuantity(((Number) quantityInputGroup.getValue()).intValue());

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
