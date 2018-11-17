import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class UserList {

    // ArrayList with all Users
    private ArrayList<User> users = new ArrayList<>();

    // Confirms login is correct (both user and pass)
    public boolean login(String username, String password){
        boolean output = false;

        if(exists(username))
            if(password.equals(users.get(getIndexByUsername(username)).getPassword()))
                output = true;

        return output;
    }

    /**Create and Delete User Methods**/
    // Creates user and adds it to ArrayList
    public void createUser(String username, String password, String firstname, String lastname, int userType){
        users.add(new User(username, password, firstname, lastname, userType));
    }

    // Deletes user from ArrayList
    public void deleteUser(String username){
        if(exists(username))
            users.remove(getIndexByUsername(username));

    }

    /**Exists and userIndex Methods**/
    // Checks if a username is unique
    public boolean exists(String username){
        boolean userExists = false;

        for(User u : users)
            if(username.equalsIgnoreCase(u.getUsername())){
                userExists = true;
            }

        return userExists;
    }

    // Finds index of a user and returns it
    public int getIndexByUsername(String username){
        int userIndex = -1;

        for(User u : users)
            if(username.equalsIgnoreCase(u.getUsername())){
                userIndex = users.indexOf(u);
            }

        return userIndex;
    }

    /**Return User Object and Print Methods**/
    // Returns user object with specified username
    public User getUserByUsername(String username){
        User u = users.get(getIndexByUsername(username));

        return u;
    }

    // Prints list of users
    public void print(){
        for(User u : users)
            System.out.println(u.getUsername());
    }

    /**Load and Save Methods**/
    // Loads User List
    public void loadUsers() throws IOException {
        File file = new File("users.csv");
        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);

        String line;

        users.clear();

        while ((line = br.readLine()) != null) {
            String[] elements = line.split(",");
            createUser(elements[0], elements[1], elements[2], elements[3], Integer.valueOf(elements[4]));
        }
        reader.close();
    }

    // Saves User List
    public void saveUsers() throws IOException {
        File file = new File("users.csv");

        file.delete();
        file.createNewFile();

        FileWriter writer = new FileWriter("users.csv", true);
        PrintWriter pw = new PrintWriter(writer);

        for (User u : users) {
            pw.printf("%s,%s,%s,%s,%d", u.getUsername(), u.getPassword(), u.getFirstname(), u.getLastname(), u.getUserTypeInt());
            pw.printf("\n");
        }

        writer.close();
    }
}