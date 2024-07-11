import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.List;

public class GUI implements ActionListener, ListSelectionListener {

    private ArrayList<Product> products;
    private ShoppingCart shoppingCart;
    private JLabel categoryL, detailsL, productL;
    private JComboBox<String> comboBox;
    private JTable table;
    private JButton cartBtn, addCart;
    private DefaultTableModel model;
    private boolean firstPurchase;
    static WestminsterShoppingManager shoppingManager;

    public GUI(ArrayList<Product> products, WestminsterShoppingManager shoppingManager) {
        this.products = products;
        shoppingCart = new ShoppingCart();
        GUI.shoppingManager = shoppingManager;




        JFrame frame = new JFrame("Westminster Shopping Center");
        frame.setSize(600, 550);
        JPanel panel = new JPanel();
        panel.setLayout(null);

        categoryL = new JLabel("Select product Category");
        categoryL.setBounds(80, 20, 160, 25);
        panel.add(categoryL);

        comboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        comboBox.setSelectedItem("All");
        comboBox.addActionListener(this);
        comboBox.setBounds(240, 20, 160, 25);
        panel.add(comboBox);

        // Table
        model = new DefaultTableModel(new String[]{"Product ID", "Name", "Category", "Price($)", "Info"}, 0);
        //Object[] row data = {"1","2","3"};

        table = new JTable(model);
        // Add row sorter to enable sorting
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 60, 500, 200);
        panel.add(scrollPane);



        for (Product product : products) {
            Object[] productArray = {product.getProductId(), product.getProductName(),product.getProductCategory(), String.valueOf(product.getPrice()),"Info"};
            model.addRow(productArray);
            //model.addRow(row data);
        }

        detailsL = new JLabel("Selected Product - Details");
        Font f = new Font("", Font.BOLD, 12);
        detailsL.setFont(f);
        detailsL.setBounds(30, 320, 200, 25);
        panel.add(detailsL);

        productL = new JLabel("<product details here>");
        productL.setBounds(30, 340, 200, 125);
        panel.add(productL);

        cartBtn = new JButton("Shopping Cart");
        cartBtn.setBounds(430, 10, 150, 25);
        cartBtn.addActionListener( this);
        panel.add(cartBtn);



        addCart = new JButton("Add to Shopping Cart");
        addCart.setBounds(200, 470, 170, 25);
        addCart.addActionListener(this);
        panel.add(addCart);



        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }


    //Frame of shopping cart
    public void shoppingCartFrame(){
        JFrame frame = new JFrame("Shopping Cart");
        frame.setSize(600,450);
        JPanel panel=new JPanel();
        panel.setLayout(null);

        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Product","Quantity","Price"}, 0);

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


    }

    public void openGUI() {
        shoppingCartFrame();
    }


    class ProductQuantity {
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



    // ActionListener and ListSelectionListener methods

    public static void main(String[] args) {
        ArrayList<Product> productsList = new ArrayList<>();
        GUI gui = new GUI(productsList, shoppingManager);
    }

    //cart button pressing
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() ==cartBtn) {
            ShoppingCartFrame shoppingCartFrame = new ShoppingCartFrame();
            shoppingCartFrame.displayShoppingCartFrame(shoppingCart);

        }
    }



    void updateProductTable() {
        String selectedCategory = Objects.requireNonNull(comboBox.getSelectedItem()).toString();
        model.setRowCount(0);

        // Add rows for current products
        for (Product product : products) {
            String productCategory;
            if (product.getProductId().startsWith("E")) {
                productCategory = "Electronic";
            } else if (product.getProductId().startsWith("C")) {
                productCategory = "Clothing";
            } else {
                productCategory = "Other";
            }

            if (selectedCategory.equals("All") || productCategory.equals(selectedCategory)) {
                Object[] productArray = {product.getProductId(), product.getProductName(),
                        product.getProductCategory(), String.valueOf(product.getPrice()), "Info"};
                model.addRow(productArray);
            }
        }




        // Add rows for stored products
        // ...
        List<Product> storedProducts = shoppingManager.getInventory();
        for (Product storedProduct : storedProducts) {
            String storedProductCategory;
            if (storedProduct.getProductId().startsWith("E")) {
                storedProductCategory = "Electronic";
            } else if (storedProduct.getProductId().startsWith("C")) {
                storedProductCategory = "Clothing";
            } else {
                storedProductCategory = "Other";
            }

            Object[] storedProductArray = {
                    storedProduct.getProductId(),
                    storedProduct.getProductName(),
                    storedProductCategory,
                    String.valueOf(storedProduct.getPrice()),
                    "Info"
            };
            model.addRow(storedProductArray);
        }
    }



// menu of selected product details
    private void displayProductDetails(Product product) {
        AbstractButton productDetailsPanel = null;
        productDetailsPanel.removeAll();
        productDetailsPanel.setLayout(new GridLayout(5, 1));
        productDetailsPanel.add(new JLabel("ID: " + product.getProductId()));
        productDetailsPanel.add(new JLabel("Name: " + product.getProductName()));
        productDetailsPanel.add(new JLabel("Category: " + product.getProductCategory()));
        productDetailsPanel.add(new JLabel("Price: $" + product.getPrice()));
        productDetailsPanel.add(new JLabel("Info: " + "<additional product info here>"));
        productDetailsPanel.revalidate();
        productDetailsPanel.repaint();

        JFrame detailsFrame = new JFrame("Product Details");
        detailsFrame.setSize(300, 200);
        detailsFrame.add(productDetailsPanel);
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Close only the details frame
        detailsFrame.setLocationRelativeTo(null);
        detailsFrame.setVisible(true);
    }
    private int getAvailabilityForRow(int row) {
        return row % 5;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'valueChanged'");
    }

}