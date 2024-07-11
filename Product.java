import java.io.Serializable;
import java.util.ArrayList;

public abstract class Product implements Serializable {

    private String productId;
    private String productName;
    private  int numOfAvailableItems;
    private double price;

    private String getProductCategory;

    Product(String productId, String productName, int numOfAvailableItems, double price)
    {
        this.productId=productId;
        this.productName=productName;
        this.numOfAvailableItems =numOfAvailableItems;
        this.price=price;


        //getters and setters
    }
    public String getProductId()
    {
        return productId;
    }
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    public String getProductName()
    {
        return productName;
    }
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    public int getNumOfAvailableItems()
    {
        return numOfAvailableItems;
    }
    public void setNumOfAvailableItems(int numOfAvailableItems )
    {
        this.numOfAvailableItems= numOfAvailableItems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public abstract double calculateTotalCost(int quantity);

    public String getProductCategory(){
        return getProductCategory;
    }

    public String getGetProductCategory() {
        return getProductCategory;
    }

    public void setGetProductCategory(String getProductCategory) {
        this.getProductCategory = getProductCategory;
    }
}
interface ShoppingManager
{
    void displayMenu();
    void addProductFromInventory(Product product);

    void removeProductFromInventory(String productId);


    void displayInventory();

    void displayShoppingCart(User user);
    void checkout(User user);


    ArrayList<Product> getInventory();
}









