import java.util.InputMismatchException;

public class Food extends Product{

    // Instance Variables
    private TypeOfFood type;
    private double mass;

    // Constructor
    public Food(String upn, String name, double price, int quantity, double mass, int type){
        super(upn, name, price, quantity);
        this.mass = mass;
        decideType(type);
    }

    // Decides what TypeOfBeverage to assign depending on the integer received
    private void decideType(int type) throws InputMismatchException{
        switch(type){
            case 1:
                this.type = TypeOfFood.CANDY;
                break;
            case 2:
                this.type = TypeOfFood.BREAD;
                break;
            case 3:
                this.type = TypeOfFood.CHIPS;
                break;
            case 4:
                this.type = TypeOfFood.SANDWITCH;
                break;
            default:
                this.type = TypeOfFood.OTHER;
                break;
        }
    }

    // Accessors
    public TypeOfFood getType(){
        return type;
    }

    public int getTypeInt(){
        int output = 0;

        switch (type){
            case CANDY:
                output = 1;
                break;
            case BREAD:
                output = 2;
                break;
            case CHIPS:
                output = 3;
                break;
            case SANDWITCH:
                output = 4;
                break;
            case OTHER:
                output = 5;
                break;
        }

        return output;
    }

    public double getMass(){
        return mass;
    }

    // Mutators
    public void setType(int type){
        decideType(type);
    }

    public void setMass(double mass){
        this.mass = mass;
    }

}