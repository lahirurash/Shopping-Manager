
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;

public class ShoppingCart {
    public Map<Product, Integer> products;
    public ShoppingCart shoppingCart;

    public ShoppingCart() {
        this.products = new HashMap<>();
    }

    public void addProductToCart(Product product) {
        shoppingCart.addToCart(product);
        int quantity = products.getOrDefault(product, 0);
        products.put(product, quantity + 1);

    }

    private void addToCart(Product product) {
        int quantity = products.getOrDefault(product, 0);
        products.put(product, quantity + 1);
    }


    public void removeFromCart(Product product) {
        int quantity = products.getOrDefault(product, 0);
        if (quantity > 0) {
            products.put(product, quantity - 1);
        }
    }

    public double calculateTotalCost() {
        double totalCost = 0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            totalCost += product.getPrice() * quantity;
        }
        return totalCost;
    }

    public Map<Product, Integer> getProducts() {
        return new HashMap<>(products);
    }

    public int getProductQuantity(Product product) {
        return products.getOrDefault(product, 0);
    }


    public void addProduct(Product selectedProduct) {
    }

    public ArrayList<ProductQuantity> getProductList() {
        ArrayList<ProductQuantity> productList = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            productList.add(new ProductQuantity(product, quantity));
        }
        return productList;
    }
    public static class ProductQuantity {
        private Product product;
        private int quantity;

        public ProductQuantity(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}