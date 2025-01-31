import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Interface for store operations
interface StoreOperations {
    void addProduct(Product product);
    void buyProduct(String productId);
    void viewCart();
    void checkout();
}

// Abstract class for Product
abstract class Product {
    protected String productID;
    protected String name;
    protected double price;
    protected int stock;

    public Product(String productID, String name, double price, int stock) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void reduceStock(int quantity) {
        this.stock -= quantity;
    }
}

// Concrete class for OnlineStore
class OnlineStore implements StoreOperations {
    private List<Product> inventory;
    private List<Product> cart;

    public OnlineStore() {
        this.inventory = new ArrayList<>();
        this.cart = new ArrayList<>();
    }

    @Override
    public void addProduct(Product product) {
        inventory.add(product);
    }

    @Override
    public void buyProduct(String productId) {
        for (Product product : inventory) {
            if (product.getProductID().equals(productId) && product.getStock() > 0) {
                cart.add(product);
                product.reduceStock(1);
                System.out.println("Added " + product.getName() + " to cart.");
                return;
            }
        }
        System.out.println("Product not found or out of stock.");
    }

    @Override
    public void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        System.out.println("Items in your cart:");
        for (Product product : cart) {
            System.out.println(product.getName() + " - $" + product.getPrice());
        }
    }

    @Override
    public void checkout() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty. Cannot checkout.");
            return;
        }
        double total = 0;
        for (Product product : cart) {
            total += product.getPrice();
        }
        System.out.println("Total amount: $" + total);
        System.out.println("Thank you for your purchase!");
        cart.clear(); // Clear the cart after checkout
    }

    public void displayProducts() {
        System.out.println("Available Products:");
        for (Product product : inventory) {
            System.out.println(product.getProductID() + ": " + product.getName() + " - $" + product.getPrice() + " (Stock: " + product.getStock() + ")");
        }
    }
}

// Concrete class for a specific product
class SimpleProduct extends Product {
    public SimpleProduct(String productID, String name, double price, int stock) {
        super(productID, name, price, stock);
    }
}

// Main class to run the online store
public class Project1 {
    public static void main(String[] args) {
        OnlineStore store = new OnlineStore();
        store.addProduct(new SimpleProduct("001", "Laptop", 999.99, 10));
        store.addProduct(new SimpleProduct("002", "Smartphone", 499.99, 20));
        store.addProduct(new SimpleProduct("003", "Headphones", 199.99, 15));

        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.println("\nEnter a command (view, add, cart, checkout, exit):");
            command = scanner.nextLine().toLowerCase();

            switch (command) {
                case "view":
                    store.displayProducts();
                    break;
                case "add":
                    System.out.println("Enter product ID to add to cart:");
                    String productId = scanner.nextLine();
                    store.buyProduct(productId);
                    break;
                case "cart":
                    store.viewCart();
                    break;
                case "checkout":
                    store.checkout();
                    break;
                case "exit":
                    System.out.println("Exiting the store. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }
    }
}