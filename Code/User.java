public class User {

    private String username;
    private String password;
    private String firstname, lastname;
    private UserType userType;

    public User(String username, String password, String firstname, String lastname, int userType){
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        decideUserType(userType);
    }

    public void decideUserType(int userType){
        switch (userType){
            case 1:
                userType = User.ADMINISTRATOR;
                break;
            case 2:
                userType = User.MANAGER;
                break;
            case 3:
                userType = User.VENDOR;
                break;
        }
    }

}
