import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Sale {

    private ArrayList<Product> items;
    private LocalDateTime date;
    private Timestamp timestamp;
    private double subtotal;
    private final double TAXRATE = 0.16;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    //Constructor
    public Sale(){
        this.items = new ArrayList<>();
        date = LocalDateTime.now();
        timestamp = Timestamp.valueOf(date);
    }

    // Accessors
    public ArrayList<Product> getItems() {
        return items;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public double getSubtotal(){
        return subtotal;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void addItem(Product item){
        items.add(item);
        subtotal += item.getTotalPrice();
    }
    
    // Imprimir con alineación los elementos del carrito de compra
    public void printCart(){
        System.out.printf("Fecha:\n");
        System.out.println(dtf.format(date));
        String titleTemplate = "%-10s %15s %15s %15s %15s%n";
        System.out.printf(titleTemplate, "Item", "Nombre", "Cantidad", "Precio U", "Precio T");
        for (int i = 0; i < items.size(); i++) {
            Product item = items.get(i);
            String template = "%-10d %15s %15d %15.2f %15.2f%n";
            System.out.printf(template, i+1, item.getName(), item.getQuantity(), item.getPrice(), item.getTotalPrice());
        }
        System.out.printf("\n%-20s %15d\n", "Productos totales:", items.size());
        System.out.printf("%-20s %15.2f\n","Subtotal:", subtotal);
        System.out.printf("%-20s %15.2f\n", "IVA:", getTax());
        System.out.printf("%-20s %15.2f\n", "Total:", getSaleTotal());
    }

    // Obtener el IVA de los productos
    private double getTax(){
        return subtotal * TAXRATE;
    }

    // Obtener el total de la compra
    private double getSaleTotal(){
        return subtotal + getTax();
    }


}
