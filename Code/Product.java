public class Product implements Cloneable{

    // Variables de la instancia
    private String upn;
    private String name;
    private double price;
    private int quantity;
    private String description;

    // Constructor
    public Product(String upn, String name, double price, int quantity){
        this.upn = upn;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(){

    }

    // Override al método clone para poder clonar este objeto
    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    // Accessors
    public String getUPN(){
        return upn;
    }

    public String getName(){
        return name;
    }

    public double getPrice(){
        return price;
    }

    public double getTotalPrice(){
        double totalPrice = this.price * this.quantity;
        return totalPrice;
    }

    public int getQuantity(){
        return quantity;
    }

    public String getDescription(){
        return description;
    }

    // Mutators
    public void setUPN(String upn){
        this.upn = upn;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public void setDescription(String description){
        this.description = description;
    }

    // toString
    public String toString(){
        return String.format("UPN: %s, Nombre: %s, Descripción: %s, Precio: %.2f, Cantidad: %d", upn, name, description, price, quantity);
    }

    // Imprimir producto
    public void print(){
        System.out.println(this.toString());
        System.out.println();
    }
}