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

    // Getter for the purchase history
    public ArrayList<Order> getPurchaseHistory() {
        return purchaseHistory;
    }

    // Getter for the payment history
    public ArrayList<Payment> getPaymentHistory() {
        return paymentHistory;
    }

    // Add a new order to purchase history
    public void addOrder(Order order) {
        this.purchaseHistory.add(order); 
    }

    // Add a new payment record
    public void addPayment(double amount, String method) {
        Payment payment = new Payment(amount, method, new Date());
        paymentHistory.add(payment);
    }

    // Add a new purchase to purchase history and update stock
    public void addPurchase(Stock stock, int quantity, String orderNumber) {
        if (stock.getQuantity() >= quantity) {
            stock.purchaseItem(quantity);  // Update stock quantity and usage
            Order newOrder = new Order(orderNumber, stock.getItemName(), quantity, new Date());
            this.purchaseHistory.add(newOrder);
            System.out.println("Order added for customer " + name + ": " + orderNumber);
        } else {
            System.out.println("Not enough stock to fulfill the order.");
        }
    }

    // View purchase history with details
    public void viewPurchaseHistory() {
        if (purchaseHistory.isEmpty()) {
            System.out.println("No purchases yet.");
        } else {
            System.out.println("Purchase History for " + name + ":");
            for (Order order : purchaseHistory) {
                System.out.printf("Order Number: %s | Item: %s | Quantity: %d | Date: %s\n", 
                                  order.getOrderNumber(), 
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

    // Display customer information and their purchase/payment history
    public void displayCustomer() {
        System.out.printf("Customer: %s | Contact: %s | Address: %s\n", name, contactInfo, address);
        
        // Display purchases if any
        if (purchaseHistory.isEmpty()) {
            System.out.println("No purchases yet.");
        } else {
            System.out.println("Purchase History:");
            for (Order order : purchaseHistory) {
                System.out.println(order);
            }
        }

        // Display payments if any
        System.out.println("Payment History:");
        if (paymentHistory.isEmpty()) {
            System.out.println("No payments yet.");
        } else {
            for (Payment payment : paymentHistory) {
                System.out.println(payment);
            }
        }
    }

    // Search for an order by order number
    public Order searchOrderByOrderNumber(String orderNumber) {
        for (Order order : purchaseHistory) {
            if (order.getOrderNumber().equals(orderNumber)) {
                return order;
            }
        }
        return null; // Return null if the order is not found
    }

    // Update contact info
    public void setContactInfo(String updatedContactInfo) {
        this.contactInfo = updatedContactInfo;
        System.out.println("Contact info updated to: " + updatedContactInfo);
    }

    // Update address
    public void setAddress(String updatedAddress) {
        this.address = updatedAddress;
        System.out.println("Address updated to: " + updatedAddress);
    }
}


   