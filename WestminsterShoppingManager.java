import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {
    public static final int MaximumProducts = 50;
    public List<Product> inventory;
    public GUI gui;

    public WestminsterShoppingManager() {
        this.inventory = new ArrayList<>();
        this.gui = null ;
        loadProductsFromFile();
    }
//Display menu
    @Override
    public void displayMenu() {
        System.out.println("Welcome to Westminster Shopping Manager!");
        System.out.println("1. Add a new product");
        System.out.println("2. Delete product");
        System.out.println("3. Print the list of products");
        System.out.println("4. Save products to file");
        System.out.println("5. Exit");
        System.out.println("6. Open GUI");
    }
// add product from inventory
    @Override
    public void addProductFromInventory(Product product) {
        if (inventory.size() < MaximumProducts) {
            inventory.add(product);
            System.out.println("Product added successfully.");
            if(gui != null) {
                gui.updateProductTable();
            }
        } else {
            System.out.println("Inventory is full. Cannot add more products.");
        }
    }
// remove product from inventory
    @Override
    public void removeProductFromInventory(String productId) {
        Iterator<Product> iterator = inventory.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getProductId().equals(productId)) {
                System.out.println("Product removed successfully:");
                displayProductInfo(product);
                iterator.remove();
                break;
            }
        }
        System.out.println("Total number of products left in the system: " + inventory.size());
    }
// display inventory
    @Override
    public void displayInventory() {
        System.out.println("List of Products");
        List<Product> sortedInventory = new ArrayList<>(inventory);
        sortedInventory.sort(Comparator.comparing(Product::getProductId));

        for (Product product : sortedInventory) {
            displayProductInfo(product);
            System.out.println();
        }
    }

    @Override
    public void displayShoppingCart(User user) {

    }

    @Override
    public void checkout(User user) {

    }

    @Override
    public ArrayList<Product> getInventory() {
        return new ArrayList<>(inventory);
    }
//display product info
    private void displayProductInfo(Product product) {
        System.out.println("Product Id: " + product.getProductId());
        System.out.println("Product Name: " + product.getProductName());
        System.out.println("Available Items: " + product.getNumOfAvailableItems());
        System.out.println("Price: " + product.getPrice());

        if (product instanceof Electronics) {
            Electronics electronicsProduct = (Electronics) product;
            System.out.println("Brand: " + electronicsProduct.getBrand());
            System.out.println("Warranty Period: " + electronicsProduct.getWarrantyPeriod());
            System.out.println("Type: Electronics");
        } else if (product instanceof Clothing) {
            Clothing clothingProduct = (Clothing) product;
            System.out.println("Size: " + clothingProduct.getSize());
            System.out.println("Color: " + clothingProduct.getColor());
            System.out.println("Type: Clothing");
        }
    }
//load product
    private void loadProductsFromFile() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Products.dat"))) {
            inventory = (List<Product>) objectInputStream.readObject();
            System.out.println("Successfully loaded products from file");
        } catch (FileNotFoundException e) {
            System.out.println("Product file not found!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
//save product
    public void saveProductsToFile() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Products.dat"))) {
            objectOutputStream.writeObject(inventory);
            System.out.println("Successfully product save to file ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openGUI() {
        ArrayList<Product> productsList = new ArrayList<>(inventory);
        this.gui = new GUI(productsList, this);
        gui.shoppingManager = this;

    }

    public static void main(String[] args) {
        GUI gui = new GUI(new ArrayList<>(), null);
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();

        // Display menu
        while (true) {
            shoppingManager.displayMenu();

            // Read user input from
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            scanner.nextLine();

            // Process user choice
            switch (choice) {
                case 1:
                    // Add a new product
                    break;
                case 2:
                    // Delete product
                    break;
                case 3:
                    // Print the list of products
                    shoppingManager.displayInventory();
                    break;
                case 4:
                    // Save products to file
                    shoppingManager.saveProductsToFile();
                    break;
                case 5:
                    // Exit the program
                    System.out.println("Exiting Westminster Shopping Manager.");
                    System.exit(0);
                    break;
                case 6:
                    // Open the GUI
                    System.out.println("Successfully open");
                    shoppingManager.openGUI();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
