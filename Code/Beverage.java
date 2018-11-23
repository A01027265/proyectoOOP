public class Beverage extends Product {

    // Variables de instancia
    private double volume;
    private TypeOfBeverage type;

    // Constructor
    public Beverage(String upn, String name, double price, int quantity, double volume, int type){
        super(upn, name, price, quantity);
        this.volume = volume;
        decideType(type);
    }

    // Decidir el tipo de bebida dependiendo del número recibido
    public void decideType(int type){
        switch(type){
            case 1:
                this.type = TypeOfBeverage.WATER;
                break;
            case 2:
                this.type = TypeOfBeverage.JUICE;
                break;
            case 3:
                this.type = TypeOfBeverage.ALCOHOL;
                break;
            case 4:
                this.type = TypeOfBeverage.SODA;
                break;
            default:
                this.type = TypeOfBeverage.OTHER;
                break;
        }
    }

    // Accessors
    public TypeOfBeverage getType(){
        return type;
    }

    public int getTypeInt(){
        int output = 0;

        switch (type){
            case WATER:
                output = 1;
                break;
            case JUICE:
                output = 2;
                break;
            case ALCOHOL:
                output = 3;
                break;
            case SODA:
                output = 4;
                break;
            case OTHER:
                output = 5;
                break;
        }

        return output;
    }

    public double getVolume(){
        return volume;
    }

    // Mutators
    public void setType(int type){
        decideType(type);
    }

    public void setVolume(double volume){
        this.volume = volume;
    }
}