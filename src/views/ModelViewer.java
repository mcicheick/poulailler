package views;

import controllers.BandeController;
import controllers.CategoryController;
import controllers.ClientController;
import data.*;
import models.*;
import tools.BCrypt;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sissoko on 14/02/2016.
 */
public class ModelViewer extends JFrame implements ModelListener {

    enum ModelType {
        USER, CLIENT, DEPENSE, TRANSACTION, CATEGORY, DEATH, OBSERVATION, PAYMENT, REMEMBER, UNKNOW
    }

    /**
     *
     */
    private static final long serialVersionUID = -9096563933302280082L;
    protected JScrollPane mainPanel;
    protected ModelView currentView;
    protected JButton retour;
    protected JMenuBar menuBar;
    protected JMenu menuFile;
    protected JMenu menuEdit;
    private String chemin;

    private static class History {

        ModelView view;
        History prev;

        public void clear() {
            prev = null;
        }
    }

    History history;

    public ModelViewer() {
        this.history = new History();
        setLayout(new BorderLayout());
        this.mainPanel = new JScrollPane();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setBackground(Color.lightGray);
        currentView = new Dashboard();
        currentView.addModelListener(this);
        history.view = currentView;
        setTitle("Les bandes");
        mainPanel.setViewportView(currentView);
        getContentPane().add(mainPanel);
        JToolBar toolBar = new JToolBar();
        retour = new JButton("Retour");
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                History prev = history.prev;
                if (prev != null) {
                    mainPanel.setViewportView(prev.view);
                    currentView = prev.view;
                    if (prev.view.dataBase.getModel() != null) {
                        setTitle(prev.view.dataBase.getModel().toString());
                    }
                    currentView.dataBase.fireTableDataChanged();
                    //prev.view.dataBase.fireTableChanged(null);
                    history = prev;
                }
            }
        });
        toolBar.add(retour);
        JButton user = new JButton("Utilisateurs");
        user.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModelView view = new UserView();
                addHistory(view);
                mainPanel.setViewportView(history.view);
            }
        });
        toolBar.add(user);
        JButton client = new JButton("Clients");
        client.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModelView view = new ClientView();
                addHistory(view);
                mainPanel.setViewportView(history.view);
            }
        });
        toolBar.add(client);

        JButton home = new JButton("Home");
        home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModelView view = new Dashboard();
                addHistory(view);
                history.clear();
                mainPanel.setViewportView(history.view);
            }
        });
        toolBar.add(home, 0);

        getContentPane().add(BorderLayout.NORTH, toolBar);

        setMenuBar();
    }

    private void setMenuBar() {
        menuBar = new JMenuBar();
        menuFile = new JMenu("File");
        JMenuItem menuItemBack = new JMenuItem("Back");
        menuItemBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retour.doClick();
            }
        });
        menuItemBack.setHorizontalTextPosition(JButton.RIGHT);
        menuFile.add(menuItemBack);
        menuItemBack.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.META_DOWN_MASK));

        JMenuItem menuItemImport = new JMenuItem("Import");
        menuItemImport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(".");
                if (chemin != null) {
                    jfc = new JFileChooser(chemin);
                }
                jfc.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        return file.isDirectory()
                                || file.getName().endsWith(".txt");
                    }

                    @Override
                    public String getDescription() {
                        return "*.txt";
                    }
                });
                int option = jfc.showOpenDialog(ModelViewer.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File f = jfc.getSelectedFile();
                    chemin = f.getAbsolutePath();
                    try {
                        loadFile(f);
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        menuItemImport.setHorizontalTextPosition(JButton.RIGHT);
        menuFile.add(menuItemImport);
        menuItemImport.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));

        menuBar.add(menuFile);
        menuEdit = new JMenu("Edit");

        JMenuItem selectAll = new JMenuItem("Select All");
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.META_DOWN_MASK));
        menuEdit.add(selectAll);
        selectAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentView.table.selectAll();
            }
        });

        menuBar.add(menuEdit);
        setJMenuBar(menuBar);
    }

    /**
     *
     * @param file
     * @throws FileNotFoundException
     */
    private static void loadFile(File file) throws FileNotFoundException, ParseException {
        Scanner scanner = new Scanner(file);
        System.out.println(file);
        String type = scanner.nextLine(); // Model TYPE
        ModelType modelType = ModelType.UNKNOW;
        if(type != null) {
            modelType = ModelType.valueOf(type.toUpperCase());
        } else {
            throw new RuntimeException("Type incorrect.");
        }
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if(line == null || line.isEmpty()) {
                continue;
            }
            createModel(modelType, line);
        }
    }

    private static void createModel(ModelType modelType, String line) throws ParseException, IllegalFormatException {
        System.out.println(line);
        String[] tab;
        Bande bande;
        java.util.List<Bande> bandes;
        switch (modelType) {
            case USER: // first_name    last_name   phone   password
                tab = line.split("[\t]+");
                if(tab.length < 3) {
                    throw new RuntimeException("Donnée incorrecte. | " + line);
                }
                User user = new User();
                user.setFirst_name(tab[0]);
                user.setLast_name(tab[1]);
                user.setTelephone(tab[2]);
                if(tab.length > 3) {
                    String password = BCrypt.hashpw(tab[3], BCrypt.gensalt());
                    user.setPassword(password);
                }
                user.save();
                break;
            case CATEGORY:
                break;
            case CLIENT:
                break;
            case DEATH:
                break;
            case DEPENSE:
                tab = line.split("[\t]+", 6);
                Depense depense = new Depense();
                depense.setDepense_date(new SimpleDateFormat("dd/MM/yyyy").parse(tab[0]));
                bandes = BandeController.getInstance().select("b from Bande b where b.title = ?1", tab[1]).getResultList();
                if (!bandes.isEmpty()) {
                    bande = bandes.get(0);
                } else {
                    bande = new Bande();
                    bande.setTitle(tab[1]);
                    bande.save();
                }
                depense.setBande(bande);

                Category category;
                java.util.List<Category> categorys = CategoryController.getInstance().select("b from Category b where b.title = ?1", tab[2]).getResultList();
                if (!categorys.isEmpty()) {
                    category = categorys.get(0);
                } else {
                    category = new Category();
                    category.setTitle(tab[2]);
                    category.save();
                }
                depense.setCategory(category);

                depense.setQuantity(Integer.parseInt(tab[3]));

                depense.setUnit_price(Double.parseDouble(tab[4]));

                depense.setDescription(tab[5]);

                depense.save();
                break;
            case OBSERVATION:
                break;
            case PAYMENT:
                break;
            case REMEMBER:
                break;
            case TRANSACTION:
                // Date	Quantity	Unit price	Weight	Unit price by kilo	Bande	Client
                tab = line.split("[\t]+", 7);
                Transaction transaction = new Transaction();
                transaction.setTransaction_date(new SimpleDateFormat("dd/MM/yyyy").parse(tab[0]));

                transaction.setQuantity(Double.parseDouble(tab[1]));

                transaction.setUnit_price(Double.parseDouble(tab[2]));
                transaction.setWeight(Double.parseDouble(tab[3]));
                transaction.setPrice_by_kilo(Double.parseDouble(tab[4]));

                bandes = BandeController.getInstance().select("b from Bande b where b.title = ?1", tab[5]).getResultList();
                if (!bandes.isEmpty()) {
                    bande = bandes.get(0);
                } else {
                    bande = new Bande();
                    bande.setTitle(tab[5]);
                    bande.save();
                }
                transaction.setBande(bande);

                Client client;
                java.util.List<Client> clients = ClientController.getInstance().select("b from Client b where b.phone = ?1", tab[6]).getResultList();
                if (!clients.isEmpty()) {
                    client = clients.get(0);
                } else {
                    client = new Client();
                    client.setPhone(tab[6]);
                    client.save();
                }
                transaction.setClient(client);

                transaction.save();
                break;
                default:
        }
    }

    private void addHistory(ModelView view) {
        History newHistory = new History();
        newHistory.prev = this.history;
        newHistory.view = view;
        currentView = view;
        this.history = newHistory;
        view.setObserver(this);
        view.addModelListener(this);
    }

    @Override
    public void fireModel(Model model) {
        if (model == null || model.getId() == null) {
            return;
        }
        if (model instanceof Bande) {
            Bande bande = (Bande) model;
            setTitle(bande.toString());
            ModelDetailView view = new ModelDetailView();

            TransactionView transactionView = new TransactionView(new TransactionTable(bande.getTransactions()));
            view.addModelView("Transactions", transactionView);

            DepenseView depenseView = new DepenseView(new DepenseTable(bande.getDepenses()));
            view.addModelView("Depenses", depenseView);

            DeathView deathView = new DeathView(new DeathTable(bande.getDeaths()));
            view.addModelView("Deaths", deathView);

            ObservationView observationView = new ObservationView(new ObservationTable(bande.getObservations()));
            view.addModelView("Observations", observationView);

            RememberView rememberView = new RememberView(new RememberTable(bande.getRemembers()));
            view.addModelView("Remembers", rememberView);

            view.setModel(model);
            addHistory(view);
            mainPanel.setViewportView(currentView);
        } else if (model instanceof Transaction) {
            Transaction transaction = (Transaction) model;
            setTitle("Paiements - " + transaction.toString());
            ModelView view = new PaymentView(new PaymentTable(transaction.getPayments()));
            view.setModel(model);
            addHistory(view);
            mainPanel.setViewportView(currentView);
        } else if (model instanceof User) {
            User user = (User) model;
            setTitle("Bandes - " + user.toString());
            ModelView view = new BandeView(new BandeTable(user.getBandes()));
            view.setModel(model);
            addHistory(view);
            mainPanel.setViewportView(currentView);
        } else if (model instanceof Client) {
            Client client = (Client) model;
            setTitle("Transactions - " + client.toString());
            ModelView view = new TransactionView(new TransactionTable(client.getTransactions()));
            view.setModel(model);
            addHistory(view);
            mainPanel.setViewportView(currentView);
        }
        currentView.setObserver(this);
    }

    @Override
    public void fireEvent(String link) {
        if (currentView != null) {
            currentView.fireEvent(link);
        }
    }

    public static void main(String[] args) {
        try {
            java.lang.System.setProperty("apple.laf.useScreenMenuBar", "true");
        } catch (Exception e) {
            // try the older menu bar property
            java.lang.System.setProperty("com.apple.macos.useScreenMenuBar", "true");
        }
        ModelViewer frame = new ModelViewer();
        frame.pack();
        frame.setBounds(200, 150, 1000, 580);
        frame.setVisible(true);
    }
}
