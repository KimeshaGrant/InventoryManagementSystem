import java.util.ArrayList;
import java.util.Date;

public class Customer {
    private String name;
    private String contactInfo;
    private String address;
    private ArrayList<Order> purchaseHistory;
    private ArrayList<Payment> paymentHistory;

    public Customer(String name, String contactInfo, String address) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.address = address;
        this.purchaseHistory = new ArrayList<>();
        this.paymentHistory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;  // Set new contact info
    }

    public void setAddress(String address) {
        this.address = address;  // Set new address
    }
    // Add a purchase item to the history
    public void addPurchase(String item, int quantity) {
        purchaseHistory.add(new Order(item, quantity, new Date()));
    }

    // Add a payment record
    public void addPayment(double amount, String method) {
        paymentHistory.add(new Payment(amount, method, new Date()));
    }

    // View purchase history with details
    public void viewPurchaseHistory() {
        if (purchaseHistory.isEmpty()) {
            System.out.println("No purchases yet.");
        } else {
            System.out.println("Purchase History for " + name + ":");
            for (Order order : purchaseHistory) {
                System.out.printf("Item: %s | Quantity: %d | Date: %s\n", 
                                  order.getItem(), 
                                  order.getQuantity(), 
                                  order.getDate());
            }
        }
    }

    // View payment history
    public void viewPaymentHistory() {
        if (paymentHistory.isEmpty()) {
            System.out.println("No payments recorded.");
        } else {
            System.out.println("Payment History for " + name + ":");
            for (Payment payment : paymentHistory) {
                System.out.println(payment);
            }
        }
    }



    // Display customer information
    public void displayCustomer() {
        System.out.printf("Customer: %s | Contact: %s | Address: %s | Purchases: ", name, contactInfo, address);
        
        // Check if the purchase history is empty
        if (purchaseHistory.isEmpty()) {
            System.out.println("No purchases yet.");
        } else {
            // Print each purchase on a new line
            for (Order order : purchaseHistory) {
                System.out.printf("Item: %s | Quantity: %d | Date: %s\n", 
                    order.getItem(), 
                    order.getQuantity(), 
                    order.getDate());
    }
        }

        System.out.println("Payment History:");
        if (paymentHistory.isEmpty()) {
            System.out.println("No payments yet.");
        } else {
            for (Payment payment : paymentHistory) {
                System.out.println(payment);
            }
    } 
}

    // Getter for the purchase history
    public ArrayList<Order> getPurchaseHistory() {
        return purchaseHistory;
    }

    public ArrayList<Payment> getPaymentHistory() {
        return paymentHistory;
    }
}

class Payment {
    private double amount;
    private String method;
    private Date date;

    public Payment(double amount, String method, Date date) {
        this.amount = amount;
        this.method = method;
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("Amount: %.2f | Method: %s | Date: %s", amount, method, date);
    }
}

class Order {
    private String item;
    private int quantity;
    private Date date;

    public Order(String item, int quantity, Date date) {
        this.item = item;
        this.quantity = quantity;
        this.date = date;
    }

    public String getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public Date getDate() {
        return date;
    }
}
