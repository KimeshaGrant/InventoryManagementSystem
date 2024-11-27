import java.util.ArrayList;

public class CustomerDatabase {
    private ArrayList<Customer> customers;

    public CustomerDatabase() {
        this.customers = new ArrayList<>();
    }

    //Add a new customer
    public void addCustomer(String name, String contactInfo, String address) {
        Customer newCustomer = new Customer(name, contactInfo, address);
        customers.add(newCustomer);
        System.out.println("Customer added: " + name);
    }

    // Update customer details
    public boolean updateCustomerDetails(String name, String newContactInfo, String newAddress) {
        Customer customer = searchCustomer(name);
        if (customer != null) {
            customer.setContactInfo(newContactInfo);
            customer.setAddress(newAddress);
            System.out.println("Customer details updated: " + name);
            return true;
        }
        System.out.println("Customer not found: " + name);
        return false;
    }

    // Update this to return a single Customer, not a list
    public Customer searchCustomer(String name) {
        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(name)) {
                return customer;
            }
        }
        System.out.println("Customer not found ");
        return null;  
    }

    // Search for customer by order number 
    public Customer searchCustomerByOrder(String orderNumber) {
        for (Customer customer : customers) {
            for (Order order : customer.getPurchaseHistory()) {
                if (order.getItem().equalsIgnoreCase(orderNumber)) { 
                    return customer;
                }
            }
        }
        System.out.println("Customer with order number " + orderNumber + " not found.");
        return null; // No customer found with that order number
    }

    public void displayAllCustomers() {
        System.out.println("\n--- Customer Database ---");
        if (customers.isEmpty()) {
            System.out.println("No customers in the database.");
        } else {
            for (Customer customer : customers) {
                customer.displayCustomer();
            }
        }
    }

    // Remove a customer by name
    public boolean removeCustomer(String name) {
        Customer customer = searchCustomer(name);
        if (customer != null) {
            customers.remove(customer);
            System.out.println("Customer removed: " + name);
            return true;
        }
        return false;  // No customer found to remove
    }

}

