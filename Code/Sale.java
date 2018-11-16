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

    public Sale(){
        this.items = new ArrayList<>();
        date = LocalDateTime.now();
        timestamp = Timestamp.valueOf(date);
    }

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
    

    public void printCart(){
        System.out.printf("Fecha:\n");
        System.out.println(dtf.format(date));
        for (int i = 0; i < items.size(); i++) {
            Product item = items.get(i);
            System.out.printf("No.\t\tNombre\t\tCantidad\t\tPrecio U\t\tPrecio T\n");
            System.out.printf("%d\t\t%s\t\t%d\t\t%f\t\t%f\n", i+1, item.getName(), item.getQuantity(), item.getPrice(), item.getTotalPrice());
        }
        System.out.printf("\nProductos totales: %d\n", items.size());
        System.out.printf("Subtotal: %f\n", subtotal);
        System.out.printf("IVA: %f\n", getTax());
        System.out.printf("Total: %f\n", getSaleTotal());
    }

    private double getTotalPrice(int quantity, double price){
        return quantity*price;
    }

    private double getTax(){
        return subtotal * TAXRATE;
    }

    private double getSaleTotal(){
        return subtotal + getTax();
    }


}
