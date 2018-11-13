public class Product implements Cloneable{
    protected String upn;
    protected String name;
    protected double price;
    protected int quantity;

    public Product(String upn, String name, double price, int quantity){

    }

    public Object clone() throws CloneNotSupportedException{
       return super.clone();
   }
}
