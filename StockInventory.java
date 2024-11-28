import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class StockInventory {
    private List<Stock> inventory;
    private List<InventoryChange> inventoryChanges;

    public StockInventory() {
        this.inventory = new ArrayList<>();
        this.inventoryChanges = new ArrayList<>();
    }
    // add item to inventory
    public void addStock(String itemName, String staffId, int quantity, double price) {
        Stock newStock = new Stock(itemName, staffId, quantity, price);
        inventory.add(newStock);
        System.out.println("Stock added: " + itemName);

    //delete item from inventory
    public void removeStock (String itemName, String staffId, int quantity, String reasonForChange, double price) {
            for (Stock stock : inventory) {
            if (stock.getItemName().equalsIgnoreCase(itemName)) {

                // Record the inventory change before removing
                InventoryChange change = new InventoryChange(staffId, reasonForChange, stock.getQuantity(), 0, 0.0);
                inventoryChanges.add(change);

                // Remove the stock item from the inventory
                inventory.remove(stock);
                System.out.println("Stock removed: " + itemName);
                return; 
            }
        }
        System.out.println("Stock item not found: " + itemName); // Item not found
    }
        
    }
    //update and track change
    public void updateStock(String itemName, int newQuantity String staffId, String reasonForChange, double salesAdjustment) {
        for (Stock stock : inventory) {
            if (stock.getItemName().equalsIgnoreCase(itemName)) {
                int previousQuantity = stock.getQuantity();
                stock.updateQuantity(newQuantity);

                // Record the inventory change
                InventoryChange change = new InventoryChange(staffId, reasonForChange, previousQuantity, newQuantity, salesAdjustment);
                inventoryChanges.add(change);

                System.out.println("Updated quantity for: " + itemName);
                return;
            }
        }
        System.out.println("Stock item not found: " + itemName);
    }

    public void displayInventory() {
        System.out.println("\n--- Current Inventory ---");
        if (inventory.isEmpty()) {
            System.out.println("No items in the inventory.");
        } else {
            for (Stock stock : inventory) {
                stock.displayStock();
            }
        }
    }

    public Stock searchStock(String itemName) {
        for (Stock stock : inventory) {
            if (stock.getItemName().equalsIgnoreCase(itemName)) {
                return stock;
            }
        }
        return null;
    }

    public void generateInventoryChangeReport() {
        System.out.println("\n--- Inventory Change Report ---");
        System.out.println("| Staff ID | Reason for Change | Date and Time | Previous Quantity | New Quantity | Sales Adjustment |");
        System.out.println("|----------|-------------------|---------------|-------------------|---------------|------------------|");

        for (InventoryChange change : inventoryChanges) {
            System.out.println(change);
        }
    }

    public void exportReport(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("Inventory Report");
            writer.println("================");
            for (Stock stock : inventory) {
                writer.printf("Item: %s | Quantity: %d | Price: $%.2f%n",
                        stock.getItemName(), stock.getQuantity(), stock.getPrice());
            }
            System.out.println("Report exported to " + fileName);
        } catch (IOException e) {
            System.out.println("Error exporting report: " + e.getMessage());
        }
    }

    // Method to calculate total inventory value
    public double calculateInventoryValue() {
        double totalValue = 0.0;
        for (Stock stock : inventory) {
            totalValue += stock.getQuantity() * stock.getPrice();
        }
        return totalValue;
    }
    
    // Method to load stock items from a file
    public void loadFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip header line
                if (line.startsWith("Item Name")) continue;

                String[] data = line.split(",");
                String itemName = data[0].trim();
                int quantity = Integer.parseInt(data[1].trim());
                double price = Double.parseDouble(data[2].trim());

                addStock(itemName, quantity, price);
            }
            System.out.println("Inventory loaded from file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error loading inventory from file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid data format in file: " + e.getMessage());
        }
    }

    // Method to generate random stock items for testing
    public void generateRandomStockItems(int numItems) {
        Random rand = new Random();
        for (int i = 0; i < numItems; i++) {
            String itemName = "Item" + (i + 1);
            int quantity = rand.nextInt(100) + 1; // Random quantity between 1 and 100
            double price = rand.nextDouble() * 100; // Random price between 0 and 100
            addStock(itemName, quantity, price);
        }
        System.out.println("Generated " + numItems + " random stock items.");
    }
}




