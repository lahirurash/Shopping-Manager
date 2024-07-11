import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Map<String, Product> productsMap = new HashMap<>();
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Scanner scanner = new Scanner(System.in);
// Display menu
        int choice;
        do {
            shoppingManager.displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addNewProduct(shoppingManager, scanner, productsMap);
                    break;
                case 2:
                    // Delete a product
                    deleteProduct(shoppingManager, scanner);
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
                    // Quit the program
                    System.out.println("Exiting Westminster Shopping Manager. Goodbye!");
                    break;
                case 6:
                    // Open GUI
                    System.out.println("Successfully open GUI ");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 6);
        scanner.close();
        GUI gui = new GUI(new ArrayList<>(productsMap.values()), shoppingManager);
    }
//delete product

    private static void deleteProduct(WestminsterShoppingManager shoppingManager, Scanner scanner) {
        System.out.println("Enter the product ID to delete: ");
        String productId = scanner.nextLine();
        shoppingManager.removeProductFromInventory(productId);
    }
//add new product
    private static void addNewProduct(WestminsterShoppingManager shoppingManager, Scanner scanner, Map<String, Product> productsMap) {
        System.out.println("Enter product type (1 for Electronics, 2 for Clothing): ");
        int type = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter product ID: ");
        String productId = scanner.nextLine();

        System.out.println("Enter product name: ");
        String productName = scanner.nextLine();

        System.out.println("Enter available items: ");
        int availableItems = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        Product product;
        if (type == 1) {
            // Electronics
            System.out.println("Enter brand: ");
            String brand = scanner.nextLine();

            System.out.println("Enter warranty period (in months): ");
            int warrantyPeriod = scanner.nextInt();
            scanner.nextLine();

            product = new Electronics(productId, productName, availableItems, price, brand, warrantyPeriod);
        } else if (type == 2) {
            // Clothing
            System.out.println("Enter size: ");
            String size = scanner.nextLine();

            System.out.println("Enter color: ");
            String color = scanner.nextLine();

            product = new Clothing(productId, productName, availableItems, price, size, color);
        } else {
            System.out.println("Invalid product type. Please enter 1 for Electronics or 2 for Clothing.");
            return;
        }

        shoppingManager.addProductFromInventory(product);
        productsMap.put(productId, product);
    }
}
