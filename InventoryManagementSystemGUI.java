import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.util.ArrayList;

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
        titleLabel.setBounds(0, 50, 400, 20

        );
        // Load the image (make sure the path is correct)
        ImageIcon splashImage = new ImageIcon("uwi.png"); // Change to your image path
        JLabel imageLabel = new JLabel(splashImage);
        imageLabel.setBounds(50, 80 
        , splashImage.getIconWidth(), splashImage.getIconHeight());

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

        addButton.addActionListener(e -> {
            String itemName = itemNameField.getText();
            try {
                int quantity = Integer.parseInt(quantityField.getText());
                double price = Double.parseDouble(priceField.getText());
                stockInventory.addStock(itemName, quantity, price);

                DefaultTableModel model = (DefaultTableModel) stockTable.getModel();
                model.addRow(new Object[]{itemName, quantity, price});

                JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Stock added successfully.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Invalid quantity or price. Please enter valid numbers.");
            }
        });

        addButton.addActionListener(e -> {
            String itemName = itemNameField.getText();
            try {
                int quantity = Integer.parseInt(quantityField.getText());
                double price = Double.parseDouble(priceField.getText());
                stockInventory.addStock(itemName, quantity, price);
        
                DefaultTableModel model = (DefaultTableModel) stockTable.getModel();
                model.addRow(new Object[]{itemName, quantity, price});
        
                // Check if stock is below 10 and notify
                if (quantity < 10) {
                    JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), 
                        "Warning: Stock for " + itemName + " is below 10.", 
                        "Stock Alert", JOptionPane.WARNING_MESSAGE);
                }
        
                JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Stock added successfully.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Invalid quantity or price. Please enter valid numbers.");
            }
        });
        // noti
        updateButton.addActionListener(e -> {
            String itemName = itemNameField.getText();
            try {
                int quantity = Integer.parseInt(quantityField.getText());
                stockInventory.updateStock(itemName, quantity);
        
                DefaultTableModel model = (DefaultTableModel) stockTable.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (model.getValueAt(i, 0).equals(itemName)) {
                        model.setValueAt(quantity, i, 1);
                        break;
                    }
                }
        
                // Check if stock is below 10 and notify
                if (quantity < 10) {
                    JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), 
                        "Warning: Stock for " + itemName + " is below 10.", 
                        "Stock Alert", JOptionPane.WARNING_MESSAGE);
                }
        
                JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Stock updated successfully.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Invalid quantity. Please enter a valid number.");
            }
        });
        
        updateButton.addActionListener(e -> {
            String itemName = itemNameField.getText();
            try {
                int quantity = Integer.parseInt(quantityField.getText());
                stockInventory.updateStock(itemName, quantity);

                DefaultTableModel model = (DefaultTableModel) stockTable.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (model.getValueAt(i, 0).equals(itemName)) {
                        model.setValueAt(quantity, i, 1);
                        break;
                    }
                }

                JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Stock updated successfully.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Invalid quantity. Please enter a valid number.");
            }
        });

        calculateButton.addActionListener(e -> {
            double totalValue = stockInventory.calculateInventoryValue();
            JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Total Inventory Value: $" + totalValue);
        });

        return inputPanel;
    }

    private void showCustomerDatabaseWindow() {
        JFrame customerFrame = createFrame("Customer Database Management", 800, 600);
        JTable customerTable = createTable(new Object[]{"Name", "Contact Info"});
        JPanel inputPanel = createCustomerInputPanel(customerTable);

        customerFrame.add(new JScrollPane(customerTable), BorderLayout.CENTER);
        customerFrame.add(inputPanel, BorderLayout.NORTH);
        customerFrame.setVisible(true);
    }

    private JPanel createCustomerInputPanel(JTable customerTable) {
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField nameField = new JTextField();
        JTextField contactField = new JTextField();
        JButton addButton = new JButton("Add Customer");

        addButton.setBackground(new Color(0, 123, 255));
        addButton.setForeground(Color.WHITE);

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Contact Info:"));
        inputPanel.add(contactField);
        inputPanel.add(new JLabel("")); // Spacer
        inputPanel.add(addButton);

        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String contactInfo = contactField.getText();

            customerDatabase.addCustomer(name, contactInfo);

            DefaultTableModel model = (DefaultTableModel) customerTable.getModel();
            model.addRow(new Object[]{name, contactInfo});

            JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Customer added successfully.");
        });

        return inputPanel;
    }


    private void showSupplierDatabaseWindow() {
        JFrame supplierFrame = createFrame("Supplier Database Management", 800, 600);
        JTable supplierTable = createTable(new Object[]{"Name", "Contact Info", "Supplied Item", "Expenditure"});
        JPanel inputPanel = createSupplierInputPanel(supplierTable);
        supplierDatabase.loadFromFile(); // Load suppliers from file

        DefaultTableModel model = (DefaultTableModel) supplierTable.getModel();
        for (Supplier supplier : supplierDatabase.getSuppliers()) {
            model.addRow(new Object[]{supplier.getName(), supplier.getContactInfo(), supplier.getSuppliedItem(), supplier.getTotalExpenditures()});
        }
        supplierFrame.add(new JScrollPane(supplierTable), BorderLayout.CENTER);
        supplierFrame.add(inputPanel, BorderLayout.NORTH);
        supplierFrame.setVisible(true);

    }

    private JPanel createSupplierInputPanel(JTable supplierTable) {
        JPanel inputPanel = new JPanel(new GridLayout(7, 7, 10, 10));
        JTextField nameField = new JTextField();
        JTextField contactField = new JTextField();
        JTextField suppliedItemField = new JTextField();
        JTextField expenditureField = new JTextField();
        JButton addButton = new JButton("Add Supplier");
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
        inputPanel.add(updateButton);
        inputPanel.add(addExpenditureButton);
        inputPanel.add(returnButton);
        

        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String contactInfo = contactField.getText();
            String suppliedItem = suppliedItemField.getText();

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
}
