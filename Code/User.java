public class User {

    private String username;
    private String password;
    private String firstname, lastname;
    private UserType userType;

    // Constructor
    public User(String username, String password, String firstname, String lastname, int userType){
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        decideUserType(userType);
    }

    public User(String username, String password, String firstname, String lastname, UserType userType){
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.userType = userType;
    }

    // Método para decidir el tipo de usuario y asignar enum UserType
    private void decideUserType(int userType){
        switch (userType){
            case 1:
                this.userType = UserType.ADMINISTRATOR;
                break;
            case 2:
                this.userType = UserType.MANAGER;
                break;
            case 3:
                this.userType = UserType.VENDOR;
                break;
        }
    }

    // Accessors
    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public UserType getUserType(){
        return userType;
    }

    public int getUserTypeInt(){
        int output = 0;

        switch (userType){
            case ADMINISTRATOR:
                output = 1;
                break;
            case MANAGER:
                output = 2;
                break;
            case VENDOR:
                output = 3;
                break;
        }

        return output;
    }

    public String getFirstname(){
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFullname(){
        return firstname + " " + lastname;
    }

    // Mutators
    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setFirstname(String firstname){
        this.firstname = firstname;
    }

    public void setLastname(String lastname){
        this.lastname = lastname;
    }

}