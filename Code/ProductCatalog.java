import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ProductCatalog {

    // ArrayList with all Users
    private ArrayList<Product> products = new ArrayList<>();
    private static final String PATHNAME = "products.csv";

    public ProductCatalog(){

    }

    /**Crear y añadir productos al catálogo**/
    // Crear y añade productos al catálogo
    public void addProductToCatalog(String upn, String name, String description,
                                    double price, int quantity, int productType, int type, double massOrVol){
        switch(productType){
            case 1:
                Product food = new Food(upn, name, price, quantity, massOrVol, type);
                food.setDescription(description);
                sortAndAddToArray(food, upn);
                break;
            case 2:
                Product beverage = new Beverage(upn, name, price, quantity, massOrVol, type);
                beverage.setDescription(description);
                sortAndAddToArray(beverage, upn);
                break;
        }
    }

    // Ordenar el catálogo
    private void sortAndAddToArray(Product p, String upn){
        int index = -1;
        for (int i = 0; i < products.size(); i++) {
            for (int j = 0; j < upn.length(); j++) {
                if (j < products.get(i).getUPN().length()) {
                    char character = upn.toLowerCase().charAt(j);
                    int ascii = (int) character;
                    char characterToCompare = products.get(i).getUPN().toLowerCase().charAt(j);
                    int asciiToCompare = (int) characterToCompare;
                    if (ascii < asciiToCompare) {
                        index = i;
                        break;
                    } else if (ascii > asciiToCompare) {
                        break;
                    }
                }

            }
            if (index != -1) {
                break;
            }
        }
        if (index == -1) {
            products.add(p);
        } else{
            products.add(index, p);
        }

    }

    // Eliminar un producto del catálogo
    public void deleteProductFromCatalog(String upn){
        if(exists(upn)){
            int index = getIndex(upn);
            products.remove(index);
        }
    }

    /**Añadir y disminuir cantidad de producto**/
    // Añadir una cantidad de producto a un elemento
    public void addItemToInventory(String upn, int quantity){
        if(exists(upn)){
            int index = getIndex(upn);
            int cQuant = products.get(index).getQuantity();
            products.get(index).setQuantity(cQuant + quantity);
        }
    }

    // Disminuye la cantidad de producto de un elemento
    public void deleteItemFromInventory(String upn, int quantity){
        if(exists(upn)){
            int index = getIndex(upn);
            int cQuant = products.get(index).getQuantity();
            products.get(index).setQuantity(cQuant - quantity);

            if(products.get(index).getQuantity() < 0)
                products.get(index).setQuantity(0);
        }
    }

    /**Métodos getProductByUPN, exists, getIndex, and print**/
    // Obtener un producto por su UPN
    public Product getProductByUPN(String upn){
        int index = getIndex(upn);

        return products.get(index);
    }

    // Verificar si un producto existe
    public boolean exists(String upn){
        boolean productExists = false;

        for(Product p : products)
            if(upn.equalsIgnoreCase(p.getUPN())){
                productExists = true;
            }

        return productExists;
    }

    // Obtener el index de un producto a través de upn
    private int getIndex(String upn){
        int index = -1;

        for(Product p : products)
            if(upn.equalsIgnoreCase(p.getUPN()))
                index = products.indexOf(p);

        return index;
    }

    // Imprime los productos del catálogo
    public void print(){
        System.out.println();
        for(Product p : products)
            p.print();
        System.out.printf("Total: %d productos\n", products.size());
    }

    /**Cargar y Guardar catálogo**/
    // Cargar el catálogo
    public void loadCatalog() throws IOException {
        File file = new File(PATHNAME);
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

    // Guardar el catálogo
    public void saveCatalog() throws IOException {
        File file = new File(PATHNAME);

        file.delete();
        file.createNewFile();

        FileWriter writer = new FileWriter(file, true);
        PrintWriter pw = new PrintWriter(writer);

        for(Product p : products) {
            int productType;
            if(p instanceof Food){
                productType = 1;
                pw.printf("%s,%s,%s,%.2f,%d,%d,%d,%.2f", p.getUPN(), p.getName(), p.getDescription(), p.getPrice(),
                        p.getQuantity(), productType, ((Food) p).getTypeInt(), ((Food) p).getMass());
                pw.printf("\n");
            }else if(p instanceof Beverage){
                productType = 2;
                pw.printf("%s,%s,%s,%.2f,%d,%d,%d,%.2f", p.getUPN(), p.getName(), p.getDescription(), p.getPrice(),
                        p.getQuantity(), productType, ((Beverage) p).getTypeInt(), ((Beverage) p).getVolume());
                pw.printf("\n");
            }
        }
        writer.close();

    }

}