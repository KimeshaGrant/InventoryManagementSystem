import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class StockInventory {
    private ArrayList <Stock> inventory;
    private ArrayList <String> outOfStockItems;  // Renamed for clarity

    public StockInventory() {
        this.inventory = new ArrayList<>();
        this.outOfStockItems = new ArrayList<>();
    }

    // Add stock to inventory
    public void addStock(String itemName, String itemID, int quantity, double price) {
        if (quantity < 0) {
            JOptionPane.showMessageDialog(null, "Cannot add negative stock!", "Invalid Quantity", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if the quantity is invalid
        }

        Stock stock = findStockByName(itemName);  // Corrected method name
        if (stock != null) {
            // Item exists, update its quantity
            stock.updateQuantity(stock.getQuantity() + quantity);
            checkStockLevel(stock);  // Check stock level after adding more
        } else {
            // Item doesn't exist, create a new Stock and add it to the inventory
            Stock newStock = new Stock(itemName, itemID, quantity, price);
            inventory.add(newStock);  // Corrected from `items.add(newStock)`
            checkStockLevel(newStock);  // Check stock level for the new stock
        }
    }

    // Update the stock of an item
    public void updateStock(String itemName, int quantity) {
        if (quantity < 0) {
            JOptionPane.showMessageDialog(null, "Cannot set a negative quantity for stock!", "Invalid Quantity", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if the quantity is invalid
        }

        Stock stock = findStockByName(itemName);
        if (stock != null) {
            // Item exists, update the quantity
            stock.updateQuantity(quantity);
            checkStockLevel(stock);  // Check stock level after updating
            if (stock.getQuantity() == 0) {
                markOutOfStock(itemName);  // Mark as out of stock if quantity is 0
            }
        } else {
            // Item not found, notify the user (optional)
            JOptionPane.showMessageDialog(null, "Item not found: " + itemName, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Search for a specific stock by its name
    public Stock findStockByName(String stockName) {
        for (Stock stock : inventory) {
            if (stock.getItemName().equalsIgnoreCase(stockName)) {
                return stock;
            }
        }
        return null;
    }

    // Check stock level and notify if stock is below 10
    private void checkStockLevel(Stock stock) {
        if (stock.getQuantity() < 10) {
            JOptionPane.showMessageDialog(null, "Warning: " + stock.getItemName() + " stock is below 10!", "Low Stock Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Mark a stock as out of stock
    public void markOutOfStock(String stockName) {
        Stock stock = findStockByName(stockName);
        if (stock != null && !outOfStockItems.contains(stockName)) {
            outOfStockItems.add(stockName);
            JOptionPane.showMessageDialog(null, stockName + " is now out of stock! Time to restock.", "Out of Stock", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Display the current stock inventory
    public void displayInventory() {
        System.out.println("\n--- Current Inventory ---");
        if (inventory.isEmpty()) {
            System.out.println("No stocks in the inventory.");
        } else {
            for (Stock stock : inventory) {
                stock.displayStock();  // Assuming Stock has a method to display itself
            }
        }
    }

    // Export the inventory report to a file
    public void exportReport(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("Inventory Report");
            writer.println("================");
            for (Stock stock : inventory) {
                writer.printf("Stock: %s | Quantity: %d | Price: $%.2f%n",
                        stock.getItemName(), stock.getQuantity(), stock.getPrice());
            }
            JOptionPane.showMessageDialog(null, "Report exported to " + fileName, "Export Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error exporting report: " + e.getMessage(), "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Calculate the total value of the inventory
    public double calculateInventoryValue() {
        double totalValue = 0.0;
        for (Stock stock : inventory) {
            totalValue += stock.getQuantity() * stock.getPrice();  // Multiply quantity by price
        }
        return totalValue;
    }

    // Get a list of out-of-stock items
    public ArrayList <String> getOutOfStockItems() {
        return outOfStockItems;
    }

    public ArrayList <Stock> loadStockFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 4) {
                    String itemName = details[0].trim();
                    String itemID = details[1].trim();
                    int quantity = Integer.parseInt(details[2].trim());
                    double price = Double.parseDouble(details[3].trim());
                    Stock stock = new Stock(itemName, itemID, quantity, price);
                    inventory.add(stock);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return inventory;
    }

    public ArrayList <Stock> getInventory() {
        return inventory;
    }
}
