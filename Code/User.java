package finalProject;

import java.io.BufferedReader;

import java.io.*;

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

    // Method to decide which user type and assign enum UserType
    public void decideUserType(int userType){
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