import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class UserList {

    // ArrayList con todos los usuarios
    private ArrayList<User> users = new ArrayList<>();
    private static final String PATHNAME = "users.csv";

    public UserList(){

    }

    // Confirmar si el login es correcto (tanto usuario como contraseña)
    public boolean login(String username, String password){
        boolean output = false;

        if(exists(username))
            if(password.equals(users.get(getIndexByUsername(username)).getPassword()))
                output = true;

        return output;
    }

    /**Métodos para Crear y Eliminar Usuarios**/
    // Crear un usuario y lo añade al arraylist
    public void createUser(String username, String password, String firstname, String lastname, int userType){
        User user = new User(username, password, firstname, lastname, userType);
        sortAndAddToArray(user, username);
    }

    // Ordenar el catálogo
    private void sortAndAddToArray(User user,String username){
        int index = -1;
        for (int i = 0; i < users.size(); i++) {
            for (int j = 0; j < username.length(); j++) {
                if (j < users.get(i).getUsername().length()) {
                    char character = username.toLowerCase().charAt(j);
                    int ascii = (int) character;
                    char characterToCompare = users.get(i).getUsername().toLowerCase().charAt(j);
                    int asciiToCompare = (int) characterToCompare;
                    if (ascii < asciiToCompare) {
                        index = i;
                        break;
                    } else if (ascii > asciiToCompare) {
                        break;
                    }
                }

            }
            if (index != -1) {
                break;
            }
        }
        if (index == -1) {
            users.add(user);
        } else{
            users.add(index, user);
        }

    }

    // Elimina un usuario del ArrayList
    public void deleteUser(String username){
        if(exists(username))
            users.remove(getIndexByUsername(username));

    }

    /**Métodos para comprobar si el usuario existe**/
    // Verifica que el usuario existe
    public boolean exists(String username){
        boolean userExists = false;

        for(User u : users)
            if(username.equals(u.getUsername())){
                userExists = true;
            }

        return userExists;
    }

    // Obtener el índice de un usuario en el ArrayList
    public int getIndexByUsername(String username){
        int userIndex = -1;

        for(User u : users)
            if(username.equalsIgnoreCase(u.getUsername())){
                userIndex = users.indexOf(u);
            }

        return userIndex;
    }

    /**Retornar el usuario**/
    // Retornar un usuario dado un username
    public User getUserByUsername(String username){
        User u = users.get(getIndexByUsername(username));

        return u;
    }

    // Imprimir la lista de los usuarios
    public void print(){
        int index = 1;
        for(User u : users) {
            System.out.printf("%d\n%s\nNombre: %s | Apellidos: %s\n", index, u.getUsername(), u.getFirstname(), u.getLastname());
            System.out.println();
            index++;
        }
        System.out.printf("Total: %d usuarios\n", users.size());
    }

    /**Métodos para cargar y guardar*/
    // Cargar la lista de usuarios
    public void loadUsers() throws IOException {
        File file = new File(PATHNAME);
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

    // Guardar la lista de usuarios
    public void saveUsers() throws IOException {
        File file = new File(PATHNAME);

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