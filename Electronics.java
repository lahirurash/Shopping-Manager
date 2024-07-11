import java.io.Serializable;

public class Electronics extends Product implements Serializable {

    private String brand;
    private int warrantyPeriod;

    public Electronics(String productId, String productName, int numOfAvailableItems, double price, String brand, int warrantyPeriod) {
        super(productId, productName, numOfAvailableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }
    public String getBrand()
    {
        return brand;
    }
    public void setBrand(String brand)
    {
        this.brand = brand;
    }
    public int getWarrantyPeriod()
    {
        return warrantyPeriod;
    }
    public void setWarrantyPeriod(int warrantyPeriod)
    {
        this.warrantyPeriod = warrantyPeriod;
    }
    @Override
    public double calculateTotalCost(int quantity)
    {
        return getPrice()*quantity;
    }
}

