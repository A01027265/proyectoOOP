public class Product implements Cloneable{

    // Instance variables
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

    // Clone override to be able to make a copy of this object
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

    // toString y print
    public String toString(){
        return "UPN: " + upn + ", Name: " + name + ", Description: " + description +
                ", Price: " + price + ", Quantity: " + quantity;
    }

    public void print(){
        System.out.println(this.toString());
    }
}