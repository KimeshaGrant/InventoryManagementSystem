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

    public ArrayList<Payment> getPaymentHistory() {
        return paymentHistory;
    }

    public void addOrder(Order order) {
        this.purchaseHistory.add(order); 
    }
    
    public Order searchOrderByOrderNumber(String orderNumber) {
        for (Order order : purchaseHistory) {
            if (order.getOrderNumber().equals(orderNumber)) {
                return order;
            }
          }
        return null; // Order not found
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;  // Set new contact info
    }

    public void setAddress(String address) {
        this.address = address;  // Set new address
    }
   

    // Add a payment record
    public void addPayment(double amount, String method) {
        Payment payment = new Payment(amount, method, new Date());
        paymentHistory.add(payment);
    }

    public void addPurchase(String item, int quantity, String orderNumber) {
        Order newOrder = new Order(orderNumber, item, quantity, new Date());
        this.purchaseHistory.add(newOrder);
        System.out.println("Order added to customer " + name + ": " + orderNumber);
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



    // Display customer information
    public void displayCustomer() {
        System.out.printf("Customer: %s | Contact: %s | Address: %s | Purchases: ", name, contactInfo, address);
        
        // Check if the purchase history is empty
        if (purchaseHistory.isEmpty()) {
            System.out.println("No purchases yet.");
        } else {
            // Print each purchase on a new line
            for (Order order : purchaseHistory) {
                System.out.println(order);
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
    
}

   