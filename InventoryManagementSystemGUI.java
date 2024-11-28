import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Main class to run the inventory management system
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
        welcomeLabel.setForeground(Color.BLACK);

        loginFrame.add(welcomeLabel, BorderLayout.NORTH);
        loginFrame.add(loginPanel, BorderLayout.CENTER);
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Check login credentials from file
            String realName = authenticateUser(username, password);
            if (realName != null) {
                JOptionPane.showMessageDialog(loginFrame, "Login successful! Welcome, " + realName + ".");
                loginFrame.dispose();
                showMainMenu();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid credentials. Please try again.");
            }
        });

        loginFrame.setVisible(true);
    }

    private String authenticateUser(String username, String password) {
        try {
            File file = new File("ResuStaffUsers.txt");  // File where usernames and passwords are stored
            if (!file.exists()) {
                return null;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                String storedUsername = credentials[0].trim();
                String storedPassword = credentials[3].trim();
                String storedName = credentials[1].trim();  // Assuming the second column is the name

                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    reader.close();
                    return storedName;  // Return the real name of the user
                }
            }
            reader.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading user data: " + e.getMessage());
        }
        return null;  // If no match is found
    }

    private void showMainMenu() {
        JFrame mainMenuFrame = new JFrame("Main Menu - RESU Inventory Management System");
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuFrame.setSize(500, 350);
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
        analyticsButton.addActionListener(e -> showAnalyticsWindow());

        mainMenuFrame.setVisible(true);
    }

    // Show Stock Inventory Window
    private void showStockInventoryWindow() {
        JFrame stockFrame = createFrame("Stock Inventory Management", 800, 600);
        JTable stockTable = createTable(new Object[]{"Item Name", "Quantity", "Price"});
        JPanel inputPanel = createStockInputPanel(stockTable);

        stockFrame.add(new JScrollPane(stockTable), BorderLayout.CENTER);
        stockFrame.add(inputPanel, BorderLayout.NORTH);
        stockFrame.setVisible(true);
    }

    // Create a new frame
    private JFrame createFrame(String title, int width, int height) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(240, 240, 240)); // Light gray background
        return frame;
    }

    // Create a new table
    private JTable createTable(Object[] columnNames) {
        return new JTable(new DefaultTableModel(columnNames, 0));
    }

    // Create stock input panel
    private JPanel createStockInputPanel(JTable stockTable) {
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField itemNameField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField priceField = new JTextField();
        JButton addButton = new JButton("Add Stock");

        // Set button styles
        addButton.setBackground(new Color(0, 123, 255));
        addButton.setForeground(Color.WHITE);

        inputPanel.add(new JLabel("Item Name:"));
        inputPanel.add(itemNameField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceField);
        inputPanel.add(addButton);

        addButton.addActionListener(e -> {
            String itemName = itemNameField.getText();
            String quantityStr = quantityField.getText();
            String priceStr = priceField.getText();

            if (!itemName.isEmpty() && !quantityStr.isEmpty() && !priceStr.isEmpty()) {
                try {
                    int quantity = Integer.parseInt(quantityStr);
                    double price = Double.parseDouble(priceStr);

                    // Add stock item to the table
                    DefaultTableModel model = (DefaultTableModel) stockTable.getModel();
                    model.addRow(new Object[]{itemName, quantity, price});
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid quantity or price.");
                }
            }
        });

        return inputPanel;
    }

    // Show Customer Database Window
    private void showCustomerDatabaseWindow() {
        JFrame customerFrame = createFrame("Customer Database Management", 800, 600);
        JTable customerTable = createTable(new Object[]{"Customer ID", "Name", "Phone"});
        JPanel inputPanel = createCustomerInputPanel(customerTable);

        customerFrame.add(new JScrollPane(customerTable), BorderLayout.CENTER);
        customerFrame.add(inputPanel, BorderLayout.NORTH);
        customerFrame.setVisible(true);
    }

    // Create customer input panel
    private JPanel createCustomerInputPanel(JTable customerTable) {
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField customerIDField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JButton addButton = new JButton("Add Customer");

        // Set button styles
        addButton.setBackground(new Color(0, 123, 255));
        addButton.setForeground(Color.WHITE);

        inputPanel.add(new JLabel("Customer ID:"));
        inputPanel.add(customerIDField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Phone:"));
        inputPanel.add(phoneField);
        inputPanel.add(addButton);

        addButton.addActionListener(e -> {
            String customerID = customerIDField.getText();
            String name = nameField.getText();
            String phone = phoneField.getText();

            if (!customerID.isEmpty() && !name.isEmpty() && !phone.isEmpty()) {
                // Add customer entry to the table
                DefaultTableModel model = (DefaultTableModel) customerTable.getModel();
                model.addRow(new Object[]{customerID, name, phone});
            }
        });

        return inputPanel;
    }

    // Show Supplier Database Window
    private void showSupplierDatabaseWindow() {
        JFrame supplierFrame = createFrame("Supplier Database Management", 800, 600);
        JTable supplierTable = createTable(new Object[]{"Name", "Contact Information", "Address", "Supplied Item", "Expenditure", "Transaction Date"});
        JPanel inputPanel = createSupplierInputPanel(supplierTable);
        SupplierDatabase supplierDatabase = new SupplierDatabase();
        supplierDatabase.loadFromFile(); // Load suppliers from file

        DefaultTableModel model = (DefaultTableModel) supplierTable.getModel();
        for (Supplier supplier : supplierDatabase.getSuppliers()) {
            model.addRow(new Object[]{supplier.getName(), supplier.getContactInfo(), supplier.getAddress(), supplier.getSuppliedItem(),supplier.getTotalExpenditures(), supplier.getTransactionDate()});
        }
        supplierFrame.add(new JScrollPane(supplierTable), BorderLayout.CENTER);
        supplierFrame.add(inputPanel, BorderLayout.NORTH);
        supplierFrame.setVisible(true);
    }

    // Create supplier input panel
    private JPanel createSupplierInputPanel(JTable supplierTable) {
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        JTextField nameField = new JTextField();
        JTextField contactField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField suppliedItemField = new JTextField();
        JTextField expenditureField = new JTextField();
        JTextField transactionDateField = new JTextField();
        JButton addButton = new JButton("Add Supplier");
        JButton updateButton = new JButton("Update Supplier");
        JButton returnButton = new JButton("Return");

        // Set button styles
        addButton.setBackground(new Color(0, 123, 255));
        updateButton.setBackground(new Color(0, 123, 255));
        returnButton.setBackground(new Color(0, 123, 255));
        addButton.setForeground(Color.WHITE);
        updateButton.setForeground(Color.WHITE);
        returnButton.setForeground(Color.WHITE);


        JPanel fieldsPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        fieldsPanel.add(new JLabel("Name:"));
        fieldsPanel.add(nameField);
        fieldsPanel.add(new JLabel("Contact Information:"));
        fieldsPanel.add(contactField);
        fieldsPanel.add(new JLabel("Address:"));
        fieldsPanel.add(addressField);
        fieldsPanel.add(new JLabel("Supplied Item:"));
        fieldsPanel.add(suppliedItemField);
        fieldsPanel.add(new JLabel("Expenditure:"));
        fieldsPanel.add(expenditureField);
        fieldsPanel.add(new JLabel("Transaction Date:"));
        fieldsPanel.add(transactionDateField);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING, 10, 10));
        buttonsPanel.add(addButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(returnButton);

        inputPanel.add(fieldsPanel, BorderLayout.CENTER);
        inputPanel.add(buttonsPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String supplierID = supplierIDField.getText();
            String name = nameField.getText();
            String contactInfo = contactField.getText();
            String address = addressField.getText(); 
            String suppliedItem = suppliedItemField.getText();
            String transactionDate = transactionDateField.getText();
            double expenditureAmount;



            //Ensure there is an input
            if (name.isEmpty() || contactInfo.isEmpty() || address.isEmpty() || suppliedItem.isEmpty() || transactionDate.isEmpty()) {
                JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "All fields must be filled out.");
                return; 
            }


            // Validate contact format
            if (!contactInfo.matches("\\d{3}-\\d{3}-\\d{4}")) {
                JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Please enter a valid contact number xxx-xxx-xxxx.");
                return;
            }


              // Validate the expenditure input
            
            try {
                expenditureAmount = Double.parseDouble(expenditureField.getText());
                if (expenditureAmount <= 0) {
                    JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Please enter a valid positive amount.");
                    return;
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Please enter a valid expenditure amount.");
                return;
            }
            

            // Validate date format
            if (!Supplier.isValidDate(transactionDate)) {
                JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Please enter a valid date yyyy/mm/dd.");
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

            supplierDatabase.addSupplier(name, contactInfo, address, suppliedItem, expenditureAmount, transactionDate);

            // Format expenditure for display
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
            String formattedExpenditure = currencyFormat.format(expenditureAmount);

            DefaultTableModel model = (DefaultTableModel) supplierTable.getModel();
            model.addRow(new Object[]{name, contactInfo, address, suppliedItem, formattedExpenditure, transactionDate});

            supplierDatabase.saveToFile();

            // Clear the text fields
            nameField.setText("");
            contactField.setText("");
            addressField.setText("");
            suppliedItemField.setText("");
            expenditureField.setText("");
            transactionDateField.setText("");
            JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Supplier added successfully.");
        });

        //Close Supplier Management window
        returnButton.addActionListener(e ->{
            JFrame topFrame = (JFrame) inputPanel.getTopLevelAncestor();
            topFrame.dispose();
         });



        updateButton.addActionListener(e -> {
            int selectedRow = supplierTable.getSelectedRow();
            if (selectedRow >= 0) {
                String name = (String) supplierTable.getValueAt(selectedRow, 0);
                String currentContactInfo = (String) supplierTable.getValueAt(selectedRow, 1);
        
                // Prompt for updated details
                String updatedContactInfo = JOptionPane.showInputDialog("Enter new contact information:", currentContactInfo);
                //Validate contact Info
                if (updatedContactInfo != null && !updatedContactInfo.matches("\\d{3}-\\d{3}-\\d{4}")) {
                    JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Please enter a valid contact number xxx-xxx-xxxx.");
                    return;
                }
        
                String updatedAddress = JOptionPane.showInputDialog("Enter new address:", supplierTable.getValueAt(selectedRow, 2));
                String updatedSuppliedItem = JOptionPane.showInputDialog("Enter updated supplied item:", supplierTable.getValueAt(selectedRow, 3));
        
                //Validate Date format
                String transactionDate = JOptionPane.showInputDialog("Enter new transaction date (yyyy/mm/dd):", supplierTable.getValueAt(selectedRow, 5));
                if (!Supplier.isValidDate(transactionDate)) {
                    JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Please enter a valid date yyyy/mm/dd.");
                    return;
                }
        
                // Validate the  updated expenditure input
                String expenditureInput = JOptionPane.showInputDialog("Enter updated expenditure amount:", supplierTable.getValueAt(selectedRow, 4));
                double updatedExpenditure = 0.0;
                try {
                    updatedExpenditure = Double.parseDouble(expenditureInput);
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Please enter a valid expenditure amount.");
                    return;
                }

        
                // Update supplier details
                supplierDatabase.updateSupplier(name, currentContactInfo, updatedContactInfo, updatedAddress, updatedSuppliedItem, transactionDate, updatedExpenditure);
        
                // Update the table
                DefaultTableModel model = (DefaultTableModel) supplierTable.getModel();
                model.setValueAt(updatedContactInfo, selectedRow, 1);
                model.setValueAt(updatedAddress, selectedRow, 2);
                model.setValueAt(updatedSuppliedItem, selectedRow, 3);
                model.setValueAt(NumberFormat.getCurrencyInstance().format(updatedExpenditure), selectedRow, 4);
                model.setValueAt(transactionDate, selectedRow, 5);
        
                JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Supplier updated successfully.");
            } else {
                JOptionPane.showMessageDialog(inputPanel.getTopLevelAncestor(), "Please select a supplier to edit.");
            }
        });

        return inputPanel;
    }

    // Show Analytics Window
    private void showAnalyticsWindow() {
        JFrame analyticsFrame = createFrame("Inventory Usage Analytics", 800, 600);
        JPanel panel = new JPanel();
        JButton generateReportButton = new JButton("Generate Report");
        JTextArea reportArea = new JTextArea(10, 30);

        panel.add(generateReportButton);
        panel.add(new JScrollPane(reportArea));

        generateReportButton.addActionListener(e -> {
            // Generate and display a sample report
            reportArea.setText("Sample Inventory Usage Report:\n\n");
            reportArea.append("Stock Item 1: 200 sold\n");
            reportArea.append("Stock Item 2: 150 sold\n");
            // You can replace this with actual data generation
        });

        analyticsFrame.add(panel, BorderLayout.CENTER);
        analyticsFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryManagementSystemGUI());
    }
}
