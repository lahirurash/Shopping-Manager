import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Iterator;

public class ShoppingCartFrame {
    private boolean firstPurchase = false;
//shopping cart
    public void displayShoppingCartFrame(ShoppingCart shoppingCart) {
        JFrame frame = new JFrame("Shopping Cart");
        frame.setSize(600, 450);
        JPanel panel = new JPanel();
        panel.setLayout(null);

        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Product", "Quantity", "Price"}, 0);

        JScrollPane scrollPane = new JScrollPane(new JTable(model));
        scrollPane.setBounds(10, 10, 580, 200);
        panel.add(scrollPane);

        ArrayList<ShoppingCart.ProductQuantity> productList = shoppingCart.getProductList();
        model.setRowCount(0);
        double total = 0;
        boolean three = false;
        int counterE = 0;
        int counterC = 0;
        double discount = 0;

        for (Iterator<ShoppingCart.ProductQuantity> iterator = productList.iterator(); iterator.hasNext(); ) {
            ShoppingCart.ProductQuantity productQuantity = iterator.next();
            Product product = productQuantity.getProduct();
            int quantity = productQuantity.getQuantity();
            Object[] arr = {product.getProductId() + " ," + product.getProductName() + "," + quantity, (quantity * product.getPrice())};
            model.addRow(arr);
            total += (quantity * product.getPrice());

            if (product.getProductCategory().equalsIgnoreCase("Electronics")) {
                counterE += quantity;
            } else if (product.getProductCategory().equalsIgnoreCase("Clothing")) {
                counterC += quantity;
            }

            if (counterE >= 3 || counterC >= 3) {
                three = true;
            }
            if (!firstPurchase) {
                discount += 0.1;
                firstPurchase = true;
            }

            if (three) {
                discount += 0.2;
            }

            double finalPrice = total - (total * discount);
            System.out.println("Final Price: $" + finalPrice);
        }

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        ShoppingCart shoppingCart = new ShoppingCart();


        ShoppingCartFrame shoppingCartFrame = new ShoppingCartFrame();
        shoppingCartFrame.displayShoppingCartFrame(shoppingCart);
    }
}
