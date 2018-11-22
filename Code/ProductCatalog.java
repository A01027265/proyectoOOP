import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ProductCatalog {

    // ArrayList with all Users
    private ArrayList<Product> products = new ArrayList<>();

    /**Create and delete product**/
    // Creates a product in catalog
    public void addProductToCatalog(String upn, String name, String description,
                                    double price, int quantity, int productType, int type, double massOrVol){
        switch(productType){
            case 1:
                Product food = new Food(upn, name, price, quantity, massOrVol, type);
                food.setDescription(description);
                products.add(food);
                break;
            case 2:
                Product beverage = new Beverage(upn, name, price, quantity, massOrVol, type);
                beverage.setDescription(description);
                products.add(beverage);
                break;
        }
    }

    // Deletes a product and all of its inventory from catalog
    public void deleteProductFromCatalog(String upn){
        if(exists(upn)){
            int index = getIndex(upn);
            products.remove(index);
        }
    }

    /**Add and delete quantity from products in inventory**/
    // Adds item quantity to a product in inventory
    public void addItemToInventory(String upn, int quantity){
        if(exists(upn)){
            int index = getIndex(upn);
            int cQuant = products.get(index).getQuantity();
            products.get(index).setQuantity(cQuant + quantity);
        }
    }

    // Deletes item quantity from a product in inventory
    public void deleteItemFromInventory(String upn, int quantity){
        if(exists(upn)){
            int index = getIndex(upn);
            int cQuant = products.get(index).getQuantity();
            products.get(index).setQuantity(cQuant - quantity);

            if(products.get(index).getQuantity() < 0)
                products.get(index).setQuantity(0);
        }
    }

    /**getProductByUPN, exists, getIndex, and print methods**/
    // Returns a product object through upn
    public Product getProductByUPN(String upn){
        int index = getIndex(upn);

        return products.get(index);
    }

    // Checks if product exists in catalog through upn
    public boolean exists(String upn){
        boolean productExists = false;

        for(Product p : products)
            if(upn.equalsIgnoreCase(p.getUPN())){
                productExists = true;
            }

        return productExists;
    }

    // Gets index of product with upn in catalog
    private int getIndex(String upn){
        int index = -1;

        for(Product p : products)
            if(upn.equalsIgnoreCase(p.getUPN()))
                index = products.indexOf(p);

        return index;
    }

    // Prints List of Products in Catalog
    public void print(){
        for(Product p : products)
            p.print();
    }

    /**Load and save Catalog**/
    // Loads Product Catalog
    public void loadCatalog() throws IOException {
        File file = new File("products.csv");
        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);

        String line;

        products.clear();

        while ((line = br.readLine()) != null) {
            String[] elements = line.split(",");
            addProductToCatalog(elements[0],elements[1],elements[2],Double.valueOf(elements[3]),
                    Integer.valueOf(elements[4]),Integer.valueOf(elements[5]),Integer.valueOf(elements[6]),
                    Double.valueOf(elements[7]));
        }
        reader.close();
    }

    // Saves Product Catalog
    public void saveCatalog() throws IOException {
        File file = new File("products.csv");

        file.delete();
        file.createNewFile();

        FileWriter writer = new FileWriter(file, true);
        PrintWriter pw = new PrintWriter(writer);

        for(Product p : products) {
            int productType;
            if(p instanceof Food){
                productType = 1;
                pw.printf("%s,%s,%s,%d,%i,%i,%i,%d", p.getUPN(), p.getName(), p.getDescription(), p.getPrice(),
                        p.getQuantity(), productType, ((Food) p).getTypeInt(), ((Food) p).getMass());
                pw.printf("\n");
            }else if(p instanceof Beverage){
                productType = 2;
                pw.printf("%s,%s,%s,%d,%i,%i,%i,%d", p.getUPN(), p.getName(), p.getDescription(), p.getPrice(),
                        p.getQuantity(), productType, ((Beverage) p).getTypeInt(), ((Beverage) p).getVolume());
                pw.printf("\n");
            }
        }
        writer.close();

    }

}