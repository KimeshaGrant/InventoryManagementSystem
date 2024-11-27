public class Supplier {
    private String name;
    private String contactInfo;
    private String suppliedItem;
    private double totalExpenditures;


    // Constructor to initialize the Supplier object
    public Supplier(String name, String contactInfo, String suppliedItem) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.suppliedItem = suppliedItem;
        this.totalExpenditures = 0.0;
    }

    // Getter methods for each attribute
    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getSuppliedItem() {
        return suppliedItem;
    }

    public void setContactInfo(String contactInfo){
        this.contactInfo=contactInfo;
    }

    public void setSuppliedItem(String suppliedItem){
        this.suppliedItem = suppliedItem;
    }

    public double getTotalExpenditures(){
        return totalExpenditures;
    }

    // Add single Expenditure to the total Expenditures
    public void addExpenditure(double amount){
        this.totalExpenditures += amount;
    }


    // Method to display the supplier's information
    public void displaySupplier() {
        System.out.printf("Supplier: %s | Contact: %s | Supplies: %s%n | Total Expenditure: %.2f%n"
        , name, contactInfo, suppliedItem, totalExpenditures);
    }
}