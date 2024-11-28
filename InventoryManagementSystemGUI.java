import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
//<<<<<<< HEAD
//import java.util.*;
//import javax.mail.*;
//import javax.mail.internet.*;
//import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
//>>>>>>> 4e43a9725b1148203ebcc8043276dc15abbab47a



public class InventoryManagementSystemGUI {

    private JFrame loginFrame;
    private StockInventory stockInventory;
    private CustomerDatabase customerDatabase;
    private SupplierDatabase supplierDatabase;

    public InventoryManagementSystemGUI() {
        stockInventory = new StockInventory();
        customerDatabase = new CustomerDatabase();
        supplierDatabase = new SupplierDatabase();

        // Show the splash screen before the login screen
        showSplashScreen();
    }

    private void showSplashScreen() {
        JWindow splashScreen = new JWindow();
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(400, 300));

        JLabel titleLabel = new JLabel("RESU Inventory Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(0, 50, 400, 20);

        // Load the image (make sure the path is correct)
        ImageIcon splashImage = new ImageIcon("uwi.png"); // Change to your image path
        JLabel imageLabel = new JLabel(splashImage);
        imageLabel.setBounds(50, 80, splashImage.getIconWidth(), splashImage.getIconHeight());

        JLabel splashLabel = new JLabel("Loading...", JLabel.CENTER);
        splashLabel.setFont(new Font("Arial", Font.BOLD, 16));
        splashLabel.setForeground(Color.BLACK);
        splashLabel.setBounds(10, 200, 400, 120);

        layeredPane.add(imageLabel, Integer.valueOf(0));
        layeredPane.add(titleLabel, Integer.valueOf(1));
        layeredPane.add(splashLabel, Integer.valueOf(1));

        splashScreen.getContentPane().add(layeredPane);
        splashScreen.pack();
        splashScreen.setLocationRelativeTo(null);
        splashScreen.setVisible(true);

        Timer timer = new Timer(3000, e -> {
            splashScreen.dispose();
            showLoginScreen();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void showLoginScreen() {
        loginFrame = new JFrame("Login - RESU Inventory Management System");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 250);
        loginFrame.setLayout(new BorderLayout());
        loginFrame.setLocationRelativeTo(null);
        loginFrame.getContentPane().setBackground(new Color(240, 240, 240)); // Light gray background

        JPanel loginPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        // Set button colors
        loginButton.setBackground(new Color(0, 123, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusable(false);

        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel("")); // Spacer
        loginPanel.add(loginButton);

        JLabel welcomeLabel = new JLabel("Welcome to RESU Inventory Management System", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setForeground((Color.BLACK)); // Change color for a more appealing effect

        loginFrame.add(welcomeLabel, BorderLayout.NORTH);
        loginFrame.add(loginPanel, BorderLayout.CENTER);
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.equals("resu") && password.equals("password123")) {
                JOptionPane.showMessageDialog(loginFrame, "Login successful! Welcome, " + username + ".");
                loginFrame.dispose();
                showMainMenu();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid credentials. Please try again.");
            }
        });

        loginFrame.setVisible(true);
    }

    private void showMainMenu() {
    JFrame mainMenuFrame = new JFrame("Main Menu - RESU Inventory Management System");
    mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainMenuFrame.setSize(500, 350);  // Increase size to accommodate new button
    mainMenuFrame.setLocationRelativeTo(null);
    mainMenuFrame.getContentPane().setBackground(new Color(240, 240, 240)); // Light gray background

    JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));  // Increased row count to 4
    JButton stockButton = new JButton("Manage Stock Inventory");
    JButton customerButton = new JButton("Manage Customer Database");
    JButton supplierButton = new JButton("Manage Supplier Database");
    JButton analyticsButton = new JButton("View Inventory Usage Analytics");

    // Set button colors
    stockButton.setBackground(new Color(0, 123, 255));
    customerButton.setBackground(new Color(0, 123, 255));
    supplierButton.setBackground(new Color(0, 123, 255));
    analyticsButton.setBackground(new Color(0, 123, 255));

    stockButton.setForeground(Color.WHITE);
    customerButton.setForeground(Color.WHITE);
    supplierButton.setForeground(Color.WHITE);
    analyticsButton.setForeground(Color.WHITE);

    buttonPanel.add(stockButton);
    buttonPanel.add(customerButton);
    buttonPanel.add(supplierButton);
    buttonPanel.add(analyticsButton);  // Add the new button

    mainMenuFrame.add(new JLabel("Select an Option:", JLabel.CENTER), BorderLayout.NORTH);
    mainMenuFrame.add(buttonPanel, BorderLayout.CENTER);

    stockButton.addActionListener(e -> showStockInventoryWindow());
    customerButton.addActionListener(e -> showCustomerDatabaseWindow());
    supplierButton.addActionListener(e -> showSupplierDatabaseWindow());

    // Add action listener for the new button
    analyticsButton.addActionListener(e -> showAnalyticsWindow());

    mainMenuFrame.setVisible(true);
}


    private void showStockInventoryWindow() {
        JFrame stockFrame = createFrame("Stock Inventory Management", 800, 600);
        JTable stockTable = createTable(new Object[]{"Item Name", "Quantity", "Price"});
        JPanel inputPanel = createStockInputPanel(stockTable);

        stockFrame.add(new JScrollPane(stockTable), BorderLayout.CENTER);
        stockFrame.add(inputPanel, BorderLayout.NORTH);
        stockFrame.setVisible(true);
    }

    private JFrame createFrame(String title, int width, int height) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(240, 240, 240)); // Light gray background
        return frame;
    }

    private JTable createTable(Object[] columnNames) {
        return new JTable(new DefaultTableModel(columnNames, 0));
    }

    private JPanel createStockInputPanel(JTable stockTable) {
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField itemNameField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField priceField = new JTextField();
        JButton addButton = new JButton("Add Stock");
        JButton updateButton = new JButton("Update Stock");
        JButton calculateButton = new JButton("Calculate Value");

        // Set button styles
        addButton.setBackground(new Color(0, 123, 255));
        updateButton.setBackground(new Color(0, 123, 255));
        calculateButton.setBackground(new Color(0, 123, 255));

        addButton.setForeground(Color.WHITE);
        updateButton.setForeground(Color.WHITE);
        calculateButton.setForeground(Color.WHITE);

        inputPanel.add(new JLabel("Item Name:"));
        inputPanel.add(itemNameField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceField);
        inputPanel.add(addButton);
        inputPanel.add(updateButton);

       


       

    }

    private void showCustomerDatabaseWindow() {
        JFrame customerFrame = createFrame("Customer Database Management", 800, 600);
        JTable customerTable = createTable(new Object[]{"Name", "Contact Info", "Address"});
        JPanel inputPanel = createCustomerInputPanel(customerTable);

        customerFrame.add(new JScrollPane(customerTable), BorderLayout.CENTER);
        customerFrame.add(inputPanel, BorderLayout.NORTH);
        customerFrame.setVisible(true);
    }

    private JPanel createCustomerInputPanel(JTable customerTable) {
        JPanel inputPanel = new JPanel(new GridLayout(9, 7, 10, 10));
        JTextField nameField = new JTextField();
        JTextField contactField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField orderNumberField = new JTextField();
        JButton addButton = new JButton("Add Customer");
        JButton searchByNameButton = new JButton("Search by Name");
        JButton searchByOrderButton = new JButton("Search by Order Number");
        JButton updateButton = new JButton("Update");
        JButton returnButton = new JButton("Return");
        JButton payButton = new JButton("Pay");
        JButton orderButton = new JButton("Add Order");

        addButton.setBackground(new Color(0, 123, 255));
        addButton.setForeground(Color.WHITE);
        searchByNameButton.setBackground(new Color(0, 123, 255));
        searchByNameButton.setForeground(Color.WHITE);
        searchByOrderButton.setBackground(new Color(0, 123, 255)); 
        searchByOrderButton.setForeground(Color.WHITE);
        updateButton.setBackground(new Color(0, 123, 255));
        updateButton.setForeground(Color.WHITE);
        returnButton.setBackground(new Color(0, 123, 255));
        returnButton.setForeground(Color.WHITE);
        payButton.setBackground(new Color(0, 123, 255));
        payButton.setForeground(Color.WHITE);
        orderButton.setBackground(new Color(0, 123, 255));
        orderButton.setForeground(Color.WHITE);
    

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Contact Info:"));
        inputPanel.add(contactField);
        inputPanel.add(new JLabel("Address:"));
        inputPanel.add(addressField);
        inputPanel.add(new JLabel("Order Number:")); 
        inputPanel.add(orderNumberField);
        inputPanel.add(new JLabel("")); // Spacer
        inputPanel.add(addButton);
        //inputPanel.add(new JLabel("Search by name or order number:"));
        inputPanel.add(searchByNameButton);
        inputPanel.add(searchByOrderButton);
        inputPanel.add(updateButton);
        inputPanel.add(returnButton);
        inputPanel.add(payButton);
        inputPanel.add(orderButton);



        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String contactInfo = contactField.getText();
            String address = addressField.getText();

            if (name.isEmpty() || contactInfo.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "All fields must be filled out.");
            } else {
                customerDatabase.addCustomer(name, contactInfo, address);

                DefaultTableModel model = (DefaultTableModel) customerTable.getModel();
                model.addRow(new Object[]{name, contactInfo, address, "View"});

                JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Customer added successfully.");
            }
            });

            searchByNameButton.addActionListener(e -> {
                String searchQuery = nameField.getText();
                Customer customer = customerDatabase.searchCustomerByName(searchQuery);
        
                if (customer != null) {
                    JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Customer found: " + customer.getName());
                } else {
                    JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Customer not found.");
                }
            });
        
            // Search by Order Number ActionListener (NEW)
            searchByOrderButton.addActionListener(e -> {
                String orderNumber = orderNumberField.getText();
                if (orderNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Please enter an order number.");
                } else {
                    Customer customer = customerDatabase.searchCustomerByOrder(orderNumber);  // Assuming the method exists
        
                    if (customer != null) {
                        JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Customer found: " + customer.getName() + " with Order Number: " + orderNumber);
                    } else {
                        JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Order Number not found.");
                    }
                }
            });

        updateButton.addActionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow >= 0) {
                
                String name = (String) customerTable.getValueAt(selectedRow, 0);
                String contactInfo = (String) customerTable.getValueAt(selectedRow, 1);
                String address = (String) customerTable.getValueAt(selectedRow, 2);
        
                
                String updatedContactInfo = JOptionPane.showInputDialog("Enter new contact info:", contactInfo);
                String updatedAddress = JOptionPane.showInputDialog("Enter new address:", address);
        
                
                if (updatedContactInfo != null && updatedAddress != null && !updatedContactInfo.isEmpty() && !updatedAddress.isEmpty()) {
                    customerDatabase.updateCustomer(name, contactInfo, updatedContactInfo, updatedAddress);
        
                    DefaultTableModel model = (DefaultTableModel) customerTable.getModel();
                    model.setValueAt(updatedContactInfo, selectedRow, 1);
                    model.setValueAt(updatedAddress, selectedRow, 2);
        
                    JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Customer updated.");
                } else {
                    JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Please provide valid contact info and address.");
                }
            } else {
                JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Please select a customer to edit.");
            }
        });

        payButton.addActionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow >= 0) {
                String name = (String) customerTable.getValueAt(selectedRow, 0);
    
                String amountStr = JOptionPane.showInputDialog("Enter payment amount:");
                String method = JOptionPane.showInputDialog("Enter payment method:");
    
                if (amountStr != null && method != null && !amountStr.isEmpty() && !method.isEmpty()) {
                    try {
                        double amount = Double.parseDouble(amountStr);
                        Customer customer = customerDatabase.searchCustomerByName(name);
                        if (customer != null) {
                            customerDatabase.addPayment(name, amount, method);
                            JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Payment added successfully.");
                        } else {
                            JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Customer not found.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Invalid amount entered.");
                    }
                } else {
                    JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "All fields must be filled out.");
                }
            } else {
                JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Please select a customer to make payment.");
            }
        });

        orderButton.addActionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow >= 0) {
                String name = (String) customerTable.getValueAt(selectedRow, 0);
    
                String item = JOptionPane.showInputDialog("Enter item:");
                String quantityStr = JOptionPane.showInputDialog("Enter quantity:");
    
                if (item != null && quantityStr != null && !item.isEmpty() && !quantityStr.isEmpty()) {
                    try {
                        int quantity = Integer.parseInt(quantityStr);
                        Customer customer = customerDatabase.searchCustomerByName(name);
                        if (customer != null) {
                            customerDatabase.addOrderToCustomer(name, item, quantity);
                            JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Order added successfully.");
                        } else {
                            JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Customer not found.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Invalid quantity entered.");
                    }
                } else {
                    JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "All fields must be filled out.");
                }
            } else {
                JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Please select a customer to add order.");
            }
        });

    returnButton.addActionListener(e -> {
        JFrame topFrame = (JFrame) inputPanel.getTopLevelAncestor();
        topFrame.dispose();
    });

        return inputPanel;
    }

    private void showSupplierDatabaseWindow() {
        JFrame supplierFrame = createFrame("Supplier Database Management", 800, 600);
        JTable supplierTable = createTable(new Object[]{"Name", "Contact Info", "Supplied Item", "Expenditure"});
        JPanel inputPanel = createSupplierInputPanel(supplierTable);
<<<<<<< HEAD

=======
        supplierDatabase.loadFromFile(); // Load suppliers from file

        DefaultTableModel model = (DefaultTableModel) supplierTable.getModel();
        for (Supplier supplier : supplierDatabase.getSuppliers()) {
            model.addRow(new Object[]{supplier.getName(), supplier.getContactInfo(), supplier.getSuppliedItem(), supplier.getTotalExpenditures()});
        }
>>>>>>> 4e43a9725b1148203ebcc8043276dc15abbab47a
        supplierFrame.add(new JScrollPane(supplierTable), BorderLayout.CENTER);
        supplierFrame.add(inputPanel, BorderLayout.NORTH);
        supplierFrame.setVisible(true);
    }

    private JPanel createSupplierInputPanel(JTable supplierTable) {
<<<<<<< HEAD
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
=======
        JPanel inputPanel = new JPanel(new GridLayout(7, 7, 10, 10));
>>>>>>> 4e43a9725b1148203ebcc8043276dc15abbab47a
        JTextField nameField = new JTextField();
        JTextField contactField = new JTextField();
        JTextField suppliedItemField = new JTextField();
        JTextField expenditureField = new JTextField();
        JButton addButton = new JButton("Add Supplier");
<<<<<<< HEAD

        addButton.setBackground(new Color(0, 123, 255));
        addButton.setForeground(Color.WHITE);
=======
        JButton updateButton = new JButton("Update");
        JButton addExpenditureButton = new JButton("Add Expenditure");
        JButton returnButton = new JButton("Return");

        addButton.setBackground(new Color(0, 123, 255));
        updateButton.setBackground(new Color(0, 123, 255));
        addExpenditureButton.setBackground(new Color(0, 123, 255));
        returnButton.setBackground(new Color(0, 123, 255));

        addButton.setForeground(Color.WHITE);
        updateButton.setForeground(Color.WHITE);
        addExpenditureButton.setForeground(Color.WHITE);
        returnButton.setForeground(Color.WHITE);
>>>>>>> 4e43a9725b1148203ebcc8043276dc15abbab47a

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Contact Info:"));
        inputPanel.add(contactField);
        inputPanel.add(new JLabel("Supplied Item:"));
        inputPanel.add(suppliedItemField);
        inputPanel.add(new JLabel("Expenditure Amount:"));
        inputPanel.add(expenditureField);
        //inputPanel.add(new JLabel("")); // Empty cell for layout purposes
        inputPanel.add(addButton);
<<<<<<< HEAD
=======
        inputPanel.add(updateButton);
        inputPanel.add(addExpenditureButton);
        inputPanel.add(returnButton);
        
>>>>>>> 4e43a9725b1148203ebcc8043276dc15abbab47a

        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String contactInfo = contactField.getText();
            String suppliedItem = suppliedItemField.getText();

<<<<<<< HEAD
            supplierDatabase.addSupplier(name, contactInfo, suppliedItem);

            DefaultTableModel model = (DefaultTableModel) supplierTable.getModel();
            model.addRow(new Object[]{name, contactInfo, suppliedItem});

            JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Supplier added successfully.");
        });

=======
            //Ensure there is an input
            if (name.isEmpty() || contactInfo.isEmpty() || suppliedItem.isEmpty()) {
                JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "All fields must be filled out.");
                return; 
            }

            //Prevent duplicating
            ArrayList<Supplier> existingSuppliers = supplierDatabase.searchSupplier(name);
            boolean isDuplicate = false;
            for (Supplier supplier : existingSuppliers) {
                if (supplier.getContactInfo().equalsIgnoreCase(contactInfo)) {
                    isDuplicate = true;
                    break;
                }
            }

            if (isDuplicate) {
                JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Supplier already exists.");
                return;
            }

            supplierDatabase.addSupplier(name, contactInfo, suppliedItem);

            DefaultTableModel model = (DefaultTableModel) supplierTable.getModel();
            model.addRow(new Object[]{name, contactInfo, suppliedItem, "$0.00"});
            
            supplierDatabase.saveToFile();
            JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Supplier added successfully.");
        });

        //Close Supplier Management window
        returnButton.addActionListener(e ->{
            JFrame topFrame = (JFrame) inputPanel.getTopLevelAncestor();
            topFrame.dispose();
         });

        addExpenditureButton.addActionListener(e -> {
            int selectedRow = supplierTable.getSelectedRow();
            if (selectedRow >= 0) {
                String name = (String) supplierTable.getValueAt(selectedRow, 0);
                String expenditureText = expenditureField.getText();
        
                try {
                    double amount = Double.parseDouble(expenditureText);
                    if (amount <= 0) {
                        JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Please enter a valid positive amount.");
                        return;
                    }
        
                    supplierDatabase.addExpenditure(name, contactField.getText(), amount);

                    // Format the expenditure as currency
                    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
                    String formattedExpenditure = currencyFormat.format(supplierDatabase.searchSupplier(name).get(0).getTotalExpenditures());
        
                    // Update the expenditure column in the table
                    DefaultTableModel model = (DefaultTableModel) supplierTable.getModel();
                    model.setValueAt(formattedExpenditure, selectedRow, 3);
        
                    // Optionally, refresh expenditure total or handle other changes
                    JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Expenditure added.");
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Please enter a valid number for expenditure.");
                }
            } else {
                JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Please select a supplier to add an expenditure.");
            }
        });

        updateButton.addActionListener(e ->{
            int selectedRow = supplierTable.getSelectedRow();
            if (selectedRow >= 0) {
                String name = (String) supplierTable.getValueAt(selectedRow, 0);
                String contactInfo = (String) supplierTable.getValueAt(selectedRow, 1);
                String suppliedItem = (String) supplierTable.getValueAt(selectedRow, 2);
    
                String updatedContactInfo = JOptionPane.showInputDialog("Enter new contact info:", contactInfo);
                String updatedSuppliedItem = JOptionPane.showInputDialog("Enter new supplied item:", suppliedItem);
    
                if (updatedContactInfo != null && updatedSuppliedItem != null) {
                    supplierDatabase.updateSupplier(name, contactInfo, updatedContactInfo, updatedSuppliedItem);
    
                    DefaultTableModel model = (DefaultTableModel) supplierTable.getModel();
                    model.setValueAt(updatedContactInfo, selectedRow, 1);
                    model.setValueAt(updatedSuppliedItem, selectedRow, 2);
    
                    JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Supplier updated.");
                }
            } else {
                JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Please select a supplier to edit.");
            }
        });
>>>>>>> 4e43a9725b1148203ebcc8043276dc15abbab47a
        return inputPanel;
    }
    private void showAnalyticsWindow() {
    // Create the window for inventory usage analytics
    JFrame analyticsFrame = createFrame("Inventory Usage Analytics", 600, 400);
    JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

    // Add a text field to enter the time range (daily, weekly, monthly)
    JTextField timeRangeField = new JTextField();
    JButton generateButton = new JButton("Generate Report");
    JButton exportButton = new JButton("Export Report");
    JButton dashboardButton = new JButton("Show Report in Dashboard");

    panel.add(new JLabel("Enter Time Range (daily, weekly, monthly):"));
    panel.add(timeRangeField);
    panel.add(generateButton);
    panel.add(exportButton);
    panel.add(dashboardButton);

    // Set button styles
    generateButton.setBackground(new Color(0, 123, 255));
    exportButton.setBackground(new Color(0, 123, 255));
    dashboardButton.setBackground(new Color(0, 123, 255));

    generateButton.setForeground(Color.WHITE);
    exportButton.setForeground(Color.WHITE);
    dashboardButton.setForeground(Color.WHITE);

    analyticsFrame.add(panel, BorderLayout.CENTER);

    // Initialize InventoryUsageAnalytics
    InventoryUsageAnalytics usageAnalytics = new InventoryUsageAnalytics(stockInventory);

    // Add action listener for generating the report
    generateButton.addActionListener(e -> {
        String timeRange = timeRangeField.getText();
        String report = usageAnalytics.generateReport(timeRange);
        JOptionPane.showMessageDialog(analyticsFrame, report, "Generated Report", JOptionPane.INFORMATION_MESSAGE);
    });

    // Add action listener for exporting the report
    exportButton.addActionListener(e -> {
        String timeRange = timeRangeField.getText();
        String report = usageAnalytics.generateReport(timeRange);
        String format = JOptionPane.showInputDialog(analyticsFrame, "Enter format (pdf/csv):");
        if (format != null) {
            usageAnalytics.exportReport(report, format);
        }
    });

    // Add action listener for showing the report in the dashboard
    dashboardButton.addActionListener(e -> {
        String timeRange = timeRangeField.getText();
        String report = usageAnalytics.generateReport(timeRange);
        usageAnalytics.showReportInDashboard(report);
    });

    analyticsFrame.setVisible(true);
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(InventoryManagementSystemGUI::new);
    }
// StockInventory class inside the same file
static class StockInventory {
    private List<Item> items;
    private List<String> outOfStockItems; // List to track out-of-stock items

    public StockInventory() {
        this.items = new ArrayList<>();
        this.outOfStockItems = new ArrayList<>();
    }

    // Add stock to inventory
    public void addStock(String itemName, int quantity, double price) {
        Item item = findItemByName(itemName);
        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
            checkStockLevel(item);  // Check stock level after adding
        } else {
            items.add(new Item(itemName, quantity, price));
            checkStockLevel(new Item(itemName, quantity, price));  // Check stock level for new item
        }
    }

    // Update the stock of an item
    public void updateStock(String itemName, int quantity) {
        Item item = findItemByName(itemName);
        if (item != null) {
            item.setQuantity(quantity);
            checkStockLevel(item);  // Check stock level after updating
            if (item.getQuantity() == 0) {
                markOutOfStock(itemName);
            }
        }
    }

    // Check stock level and notify if stock falls below 10
    private void checkStockLevel(Item item) {
        if (item.getQuantity() < 10) {
            JOptionPane.showMessageDialog(null, "Warning: " + item.getName() + " stock is below 10!", "Low Stock Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Mark an item as out of stock and add to outOfStockItems list
    public void markOutOfStock(String itemName) {
        if (!outOfStockItems.contains(itemName)) {
            outOfStockItems.add(itemName);
            // Notify the staff about the out-of-stock item
            JOptionPane.showMessageDialog(null, "Item Out of Stock: " + itemName + " is now out of stock! Time to restock.", "Out of Stock", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Get list of out-of-stock items
    public List<String> getOutOfStockItems() {
        return outOfStockItems;
    }

    // Calculate total inventory value
    public double calculateInventoryValue() {
        double totalValue = 0.0;
        for (Item item : items) {
            totalValue += item.getQuantity() * item.getPrice();
        }
        return totalValue;
    }

    private Item findItemByName(String itemName) {
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    // Item class to store item details
    public static class Item {
        private String name;
        private int quantity;
        private double price;

        public Item(String name, int quantity, double price) {
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getPrice() {
            return price;
        }
    }
}

