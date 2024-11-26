import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SupplierDatabase {
    private ArrayList<Supplier> suppliers;
    private final String fileName = "Suppliers.txt";

    public SupplierDatabase() {
        this.suppliers = new ArrayList<>();
        loadFromFile();
    }

    // Add a new supplier to the database
    public void addSupplier(String name, String contactInfo, String suppliedItem) {
        if (name == null || name.isEmpty() || contactInfo == null || contactInfo.isEmpty() || suppliedItem == null || suppliedItem.isEmpty()) {
            System.out.println("Invalid supplier data.");
            return;
        }
        //Check for dubpliacte
        for (Supplier supplier: suppliers){
            if (supplier.getName().equalsIgnoreCase(name) && supplier.getContactInfo().equalsIgnoreCase(contactInfo)) {
                System.out.println("Supplier is already added.");
                return;
            }
        }
        Supplier newSupplier = new Supplier(name, contactInfo, suppliedItem);
        suppliers.add(newSupplier);
        System.out.println("Supplier added: " + name);
    }

    // Search for suppliers by name (returns a list of matching suppliers)
    public ArrayList<Supplier> searchSupplier(String name) {
        ArrayList<Supplier> foundSuppliers = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            if (supplier.getName().equalsIgnoreCase(name)) {
                foundSuppliers.add(supplier);
            }
        }
        return foundSuppliers;
    }
     // Update a supplier's details
     public void updateSupplier(String name, String contactInfo, String updatedContactInfo, String updatedSuppliedItem) {
        for (Supplier supplier : suppliers) {
            if (supplier.getName().equalsIgnoreCase(name) && supplier.getContactInfo().equalsIgnoreCase(contactInfo)) {
                supplier.setContactInfo(updatedContactInfo);
                supplier.setSuppliedItem(updatedSuppliedItem);
                saveToFile();  // Save after editing
                System.out.println("Supplier updated: " + name);
                return;
            }
        }
        System.out.println("Supplier not found.");
    }

    public Supplier searchSupplier(String name, String contactInfo) {
        for (Supplier supplier : suppliers) {
            if (supplier.getName().equalsIgnoreCase(name) && supplier.getContactInfo().equalsIgnoreCase(contactInfo)) {
                return supplier;  // Return the matching supplier
            }
        }
        System.out.println("Supplier not found.");
        return null;
    }


    // Display all suppliers in the database
    public void displayAllSuppliers() {
        System.out.println("\n--- Supplier Database ---");
        if (suppliers.isEmpty()) {
            System.out.println("No suppliers in the database.");
        } else {
            for (Supplier supplier : suppliers) {
                supplier.displaySupplier();
            }
        }
    }
    
    public void saveToFile(){
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))){
            writer.println("Suppliers List");
            writer.println("================");
            for (Supplier supplier : suppliers){
                writer.printf("Supplier: %s | Contact: %s | Supplies: %s%n",
                 supplier.getName(), supplier.getContactInfo(), supplier.getSuppliedItem());
            }
            System.out.println("Supplier data save to" + fileName);

        } catch (IOException e){
            System.err.println("Error Saving to file:" + e.getMessage());
        }

    }

    // Load suppliers from file
    public void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("Suppliers List")) {
                    continue;  // Skip header
                }
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    String contactInfo = parts[1].trim();
                    String suppliedItem = parts[2].trim();
                    Supplier supplier = new Supplier(name, contactInfo, suppliedItem);
                    suppliers.add(supplier);
                }
            }
            System.out.println("Supplier data loaded from file.");
        } catch (IOException e) {
            System.err.println("Error loading from file: " + e.getMessage());
        }
    }

}
