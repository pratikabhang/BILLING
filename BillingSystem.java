import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

// Class representing a product
class Product {
    private String name;
    private double price;
    private int quantity;

    // Constructor for the Product class
    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getter for the product name
    public String getName() {
        return name;
    }

    // Getter for the product price
    public double getPrice() {
        return price;
    }

    // Getter for the product quantity
    public int getQuantity() {
        return quantity;
    }
}

// Class representing a shopping cart
class ShoppingCart {
    private List<Product> items;
    private double discount;

    // Constructor for the ShoppingCart class
    public ShoppingCart() {
        items = new ArrayList<>();
        discount = 0.0;
    }

    // Add a product to the cart
    public void addItem(Product product) {
        items.add(product);
    }

    // Remove a product from the cart based on the index
    public void removeItem(int index) {
        items.remove(index);
    }

    // Calculate the total cost of all items in the cart after applying the discount
    public double calculateTotal() {
        double total = 0;
        for (Product item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        total -= discount;
        return total;
    }

    // Set the discount amount
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    // Print the invoice with details of all items, the total cost, and the applied
    // discount
    public void printInvoice() {
        System.out.println("----------- INVOICE -----------");
        for (int i = 0; i < items.size(); i++) {
            Product item = items.get(i);
            System.out.println(
                    (i + 1) + ". " + item.getName() + "\t$" + item.getPrice() + "\tQty: " + item.getQuantity());
        }
        System.out.println("-------------------------------");
        System.out.println("Total: $" + calculateTotal());
        System.out.println("Discount: $" + discount);
        System.out.println("-------------------------------");
    }

    public int getCartSize() {
        return items.size();
    }

    public Product getItem(int i) {
        return items.get(i);
    }

    // Save the invoice to a file
    public void saveInvoiceToFile() {
        try (FileWriter writer = new FileWriter("invoice.txt")) {
            writer.write("----------- INVOICE -----------\n");
            for (int i = 0; i < items.size(); i++) {
                Product item = items.get(i);
                writer.write((i + 1) + ". " + item.getName() + "\t$" + item.getPrice() + "\tQty: " + item.getQuantity()
                        + "\n");
            }
            writer.write("-------------------------------\n");
            writer.write("Total: $" + calculateTotal() + "\n");
            writer.write("Discount: $" + discount + "\n");
            writer.write("-------------------------------\n");
            System.out.println("Invoice saved to file.");
        } catch (IOException e) {
            System.out.println("Failed to save invoice to file: " + e.getMessage());
        }
    }
}

// Main class representing the Billing System
public class BillingSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static ShoppingCart cart = new ShoppingCart();

    // Entry point of the program
    public static void main(String[] args) {
        showMenu();
    }

    // Display the main menu and handle user input
    private static void showMenu() {
        int choice;
        do {
            System.out.println("----- Billing System ------");
            System.out.println("1. Add item to cart");
            System.out.println("2. Remove item from cart");
            System.out.println("3. View cart");
            System.out.println("4. Apply discount");
            System.out.println("5. Generate invoice");
            System.out.println("6. Download invoice");
            System.out.println("7. Exit");
            System.out.println("----------------------------------------");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addItemToCart();
                    break;
                case 2:
                    removeItemFromCart();
                    break;
                case 3:
                    viewCart();
                    break;
                case 4:
                    applyDiscount();
                    break;
                case 5:
                    generateInvoice();
                    break;
                case 6:
                    downloadInvoice();
                    break;
                case 7:
                    System.out.println("Thank you for using the Billing System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 7);
    }

    // Add an item to the cart based on user input
    private static void addItemToCart() {
        System.out.print("Enter product name: ");
        String name = scanner.next();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        Product product = new Product(name, price, quantity);
        cart.addItem(product);

        System.out.println("Item added to cart!");
    }

    // Remove an item from the cart based on user input
    private static void removeItemFromCart() {
        System.out.print("Enter item number to remove: ");
        int index = scanner.nextInt();

        if (index >= 1 && index <= cart.getCartSize()) {
            cart.removeItem(index - 1);
            System.out.println("Item removed from cart!");
        } else {
            System.out.println("Invalid item number.");
        }
    }

    // View the current items in the cart along with the total cost
    private static void viewCart() {
        if (cart.getCartSize() > 0) {
            System.out.println("------ Cart Items ------");
            for (int i = 0; i < cart.getCartSize(); i++) {
                Product item = cart.getItem(i);
                System.out.println(
                        (i + 1) + ". " + item.getName() + "\t$" + item.getPrice() + "\tQty: " + item.getQuantity());
            }
            System.out.println("------------------------");
            System.out.println("Total: $" + cart.calculateTotal());
            System.out.println("------------------------");
        } else {
            System.out.println("Cart is empty.");
        }
    }

    // Apply a discount to the total cost based on user input
    private static void applyDiscount() {
        System.out.print("Enter discount amount: ");
        double discount = scanner.nextDouble();
        cart.setDiscount(discount);
        System.out.println("Discount applied!");
    }

    // Generate an invoice for the current cart and clear the cart
    private static void generateInvoice() {
        if (cart.getCartSize() > 0) {
            cart.printInvoice();
            cart = new ShoppingCart(); // Clear the cart after generating the invoice
        } else {
            System.out.println("Cart is empty. Cannot generate invoice.");
        }
    }

    // Download the invoice as a file
    private static void downloadInvoice() {
        if (cart.getCartSize() > 0) {
            cart.saveInvoiceToFile();
            cart = new ShoppingCart(); // Clear the cart after downloading the invoice
        } else {
            System.out.println("Cart is empty. Cannot download invoice.");
        }
    }
}
