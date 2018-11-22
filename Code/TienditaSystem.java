import java.io.*;
import java.util.*;

public class TienditaSystem{
    private static Scanner sc = new Scanner(System.in);
    private static UserList userList = new UserList();
    private static ProductCatalog catalog = new ProductCatalog();
    private static String username = "";
    private static String password = "";

    /*
    private static String catalogFileName = "/Users/tiagohernan/Documents/Tec/2sem/Progra/ProyectoFinal/catalog.csv";
    private static String usersFileName = "/Users/tiagohernan/Documents/Tec/2sem/Progra/ProyectoFinal/users.csv";
    */

    public static void main(String[] args) {
        int selectedOption;

        try {
            catalog.loadCatalog();
            userList.loadUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("*********************************************");
        System.out.println("************ Quiubo, estás en la ************");
        System.out.println("************ mejor tiendita ever ************");
        System.out.println("*********************************************");
        System.out.println("**************** TITANSHOP ******************");
        System.out.println("*********************************************");
        getUserAndPassword();
        while(!(userList.login(username, password))){
            System.out.println("Sorry, no conozco ese usuario/contraseña. Intenta otra vez");
            getUserAndPassword();
        }

        User currentUser = userList.getUserByUsername(username);

        System.out.println();
        System.out.printf("¡Hola hola! %s\n", currentUser.getFirstname());

        int userType = currentUser.getUserTypeInt();

        while(true){
            System.out.printf("Usuario: %s\n", currentUser.getUsername());
            System.out.printf("Nivel de permisos: %s\n", currentUser.getUserTypeInt());
            System.out.println();
            switch (userType){
                case 1:
                    adminPrompt();
                    break;
                case 2:
                    managerPrompt();
                    break;
                case 3:
                    vendorPrompt();
                    break;
                default:
                    adminPrompt();
                    break;
            }

            selectedOption = sc.nextInt();
            sc.nextLine();

            boolean notCorrect = true;
            while (notCorrect) {
                switch (userType){
                    case 1:
                        if ((selectedOption <= 5 && selectedOption >= 1)) {
                            notCorrect = false;
                        }
                        break;
                    case 2:
                        if ((selectedOption <= 4 && selectedOption >= 1)) {
                            notCorrect = false;
                        }
                        break;
                    case 3:
                        if ((selectedOption <= 2 && selectedOption >= 1)) {
                            notCorrect = false;
                        }
                        break;
                    default:
                        notCorrect = true;
                        break;
                }

                if (notCorrect) {
                    System.out.println("Esa opción no es válida, por favor escribe un número en el rango.");
                    System.out.print("Selecciona una opción: ");
                    selectedOption = sc.nextInt();
                    sc.nextLine();
                }
            }

            switch (selectedOption){
                case 1:
                    saveAndExit();
                    break;
                case 2:
                    sellProduct();
                    break;
                case 3:
                    manageInventory();
                    break;
                case 4:
                    manageCatalog();
                    break;
                case 5:
                    manageUsers();
                    break;
                default:
                    saveAndExit();
                    break;
            }
        }
    }

    private static void getUserAndPassword(){
        System.out.printf("Usuario: ");
        username = sc.nextLine();
        try {
            // crear un objeto de Consola
            Console cnsl = System.console();
            // si consola no es null
            if (cnsl != null) {
                // leer input de usuario
                char[] pwd = cnsl.readPassword("Contraseña: ");
                password = new String(pwd);
            } else {
                System.out.printf("Contraseña: ");
                password = sc.nextLine();
            }

        } catch(Exception ex) {
            // en caso de un error con la consola
            ex.printStackTrace();
            System.out.printf("Contraseña: ");
            password = sc.nextLine();
        }
    }

    private static void adminPrompt(){
        System.out.println("¿Qué desea hacer, administrador?");
        System.out.println("******************************");
        System.out.println("(1) Guardar y salir");
        System.out.println("(2) Vender productos");
        System.out.println("(3) Administrar inventario");
        System.out.println("(4) Administrar catálogo");
        System.out.println("(5) Administrar usuarios");
        System.out.println("******************************");
        System.out.print("Selecciona una opción: ");
    }

    private static void managerPrompt(){
        System.out.println("Señor Gerente, ¿qué quiere hacer?");
        System.out.println("******************************");
        System.out.println("(1) Guardar y salir");
        System.out.println("(2) Vender productos");
        System.out.println("(3) Administrar inventario");
        System.out.println("(4) Administrar catálogo");
        System.out.println("******************************");
        System.out.print("Selecciona una opción: ");
    }

    private static void vendorPrompt(){
        System.out.println("Vamos a hacer muchos varos $$$, estimado vendedor.");
        System.out.println("******************************");
        System.out.println("(1) Guardar y salir");
        System.out.println("(2) Vender productos");
        System.out.println("******************************");
        System.out.print("Selecciona una opción: ");
    }

    private static void sellProduct(){
        String upn;
        int quantity;
        Product product = new Product();
        Product soldProduct;
        Sale currentSale =  new Sale();
        int selectedOption = 0;
        while (selectedOption != 3) {
            System.out.println("*****************************");
            System.out.println("            Venta            ");
            System.out.println("*****************************");
            System.out.println("(1) Añadir producto a carrito de compra");
            System.out.println("(2) Ver carrito de compra");
            System.out.println("(3) Checkout");
            System.out.println("*****************************");
            System.out.print("Selecciona una opción: ");
            selectedOption = sc.nextInt();
            sc.nextLine();
            boolean wrongOption = true;
            while(wrongOption){
                wrongOption = false;
                switch (selectedOption) {
                    case 1:
                        System.out.println("*****************************");
                        System.out.println("       Añadir producto       ");
                        System.out.println("*****************************");
                        System.out.printf("UPN: ");
                        upn = sc.nextLine();
                        boolean wrongProduct = true;
                        while (wrongProduct) {
                            if (!(catalog.exists(upn))) {
                                System.out.println("Ese UPN no está registrado. Intenta de nuevo.");
                                System.out.printf("UPN: ");
                                upn = sc.nextLine();
                            } else {
                                product = catalog.getProductByUPN(upn);
                                if (product.getQuantity() == 0) {
                                    System.out.println("No hay disponibilidad de ese producto.");
                                    System.out.println("Intente con otro producto.");
                                    System.out.printf("UPN: ");
                                    upn = sc.nextLine();
                                } else{
                                    wrongProduct = false;
                                }
                            }
                        }
                        System.out.printf("Cantidad: ");
                        quantity = sc.nextInt();
                        sc.nextLine();
                        while (quantity > product.getQuantity()) {
                            System.out.println("No hay suficientes unidades. Escoge un número menor.");
                            System.out.printf("Cantidad: ");
                            quantity = sc.nextInt();
                            sc.nextLine();
                        }
                        try{
                            soldProduct = (Product)product.clone();
                            product.setQuantity(product.getQuantity() - quantity);
                            soldProduct.setQuantity(quantity);
                            currentSale.addItem(soldProduct);
                            System.out.println("Producto añadido al carrito de compra");
                        } catch(Exception e){
                            System.out.println(e);
                            System.out.println("Exception");
                        }
                        break;
                    case 2:
                        System.out.println("*****************************");
                        System.out.println("      Carrito de compra      ");
                        System.out.println("*****************************");
                        currentSale.printCart();
                        break;
                    case 3:
                        System.out.println("****************************");
                        System.out.println("          Checkout          ");
                        System.out.println("****************************");
                        System.out.println("Aquí el resumen de la compra:");
                        currentSale.printCart();
                        break;
                    default:
                        wrongOption = true;
                        System.out.println("Esa opción no es válida, por favor escribe un número en el rango.");
                        System.out.print("Selecciona una opción: ");
                        selectedOption = sc.nextInt();
                        sc.nextLine();
                        break;
                }
                save();
            }
        }
    }

    private static void manageInventory(){
        boolean wrongProduct;
        Product product;
        int selectedOption = 0;
        String upn;
        int quantity;

        while(selectedOption != 4) {
            System.out.println("******************************");
            System.out.println("    Administrar inventario    ");
            System.out.println("******************************");
            System.out.println("(1) Añadir unidades");
            System.out.println("(2) Retirar unidades");
            System.out.println("(3) Imprimir productos");
            System.out.println("(4) Regresar a menú principal");
            System.out.println("******************************");
            System.out.print("Selecciona una opción: ");
            selectedOption = sc.nextInt();
            sc.nextLine();
            boolean wrongOption = true;
            while (wrongOption) {
                wrongOption = false;
                switch (selectedOption) {
                    case 1:
                        System.out.println("*****************************");
                        System.out.println("       Añadir unidades       ");
                        System.out.println("*****************************");
                        System.out.printf("UPN: ");
                        upn = sc.nextLine();
                        while (!(catalog.exists(upn))) {
                            System.out.println("Ese UPN no está registrado. Intenta de nuevo.");
                            System.out.printf("UPN: ");
                            upn = sc.nextLine();
                        }
                        product = catalog.getProductByUPN(upn);
                        System.out.printf("Unidades actuales de ese producto: %d): ", product.getQuantity());
                        System.out.printf("Cantidad de unidades para agregar: ");
                        quantity = sc.nextInt();
                        sc.nextLine();
                        catalog.addItemToInventory(upn, quantity);
                        System.out.println("Inventario actualizado");
                        break;
                    case 2:
                        System.out.println("*****************************");
                        System.out.println("       Retirar unidades      ");
                        System.out.println("*****************************");
                        System.out.printf("UPN: ");
                        upn = sc.nextLine();
                        wrongProduct = true;
                        while (wrongProduct) {
                            if (!(catalog.exists(upn))) {
                                System.out.println("Ese UPN no está registrado. Intenta de nuevo.");
                                System.out.printf("UPN: ");
                                upn = sc.nextLine();
                            } else {
                                product = catalog.getProductByUPN(upn);
                                if (product.getQuantity() == 0) {
                                    System.out.println("No hay disponibilidad de ese producto.");
                                    System.out.println("Intente con otro producto.");
                                    System.out.printf("UPN: ");
                                    upn = sc.nextLine();
                                } else {
                                    wrongProduct = false;
                                }
                            }
                        }
                        product = catalog.getProductByUPN(upn);
                        System.out.printf("Unidades disponibles de ese producto: %d\n", product.getQuantity());
                        System.out.printf("Cantidad de unidades a retirar: ");
                        quantity = sc.nextInt();
                        sc.nextLine();
                        while (quantity > product.getQuantity()) {
                            System.out.println("No hay suficientes unidades. Escoge un número menor.");
                            System.out.printf("Cantidad de unidades a retirar: ");
                            quantity = sc.nextInt();
                            sc.nextLine();
                        }
                        catalog.deleteItemFromInventory(upn, quantity);
                        System.out.println("Inventario actualizado");
                        break;
                    case 3:
                        catalog.print();
                        break;
                    case 4:
                        break;
                    default:
                        wrongOption = true;
                        System.out.println("Esa opción no es válida, por favor escribe un número en el rango.");
                        System.out.print("Selecciona una opción: ");
                        selectedOption = sc.nextInt();
                        sc.nextLine();
                        break;
                }
                save();
            }
        }
    }

    // NECESITA REVISION
    private static void manageCatalog(){
        String upn;
        String name;
        double price;
        int quantity;
        String description;
        int productType;
        int type = 0;
        double quantityOfContent;
        int selectedOption = 0;
        Product product;

        while(selectedOption != 5) {
            System.out.println("******************************");
            System.out.println("     Administrar catálogo     ");
            System.out.println("******************************");
            System.out.println("(1) Añadir producto");
            System.out.println("(2) Eliminar producto");
            System.out.println("(3) Editar producto");
            System.out.println("(4) Imprimir catálogo");
            System.out.println("(5) Regresar a menú principal");
            System.out.println("******************************");
            System.out.print("Selecciona una opción: ");
            selectedOption = sc.nextInt();
            sc.nextLine();
            boolean wrongOption = true;
            while (wrongOption) {
                wrongOption = false;
                switch (selectedOption) {
                    case 1:
                        System.out.println("*****************************");
                        System.out.println("       Añadir producto       ");
                        System.out.println("*****************************");
                        System.out.printf("UPN: ");
                        upn = sc.nextLine();
                        while (catalog.exists(upn)) {
                            System.out.println("Ese UPN ya existe. Intenta de nuevo.");
                            System.out.printf("UPN: ");
                            upn = sc.nextLine();
                        }
                        System.out.printf("Nombre: ");
                        name = sc.nextLine();
                        System.out.printf("Descripción: ");
                        description = sc.nextLine();
                        System.out.printf("Precio (en pesos): ");
                        price = sc.nextDouble();
                        sc.nextLine();
                        System.out.printf("Cantidad inicial: ");
                        quantity = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Tipo de producto: ");
                        System.out.println("(1) Alimento");
                        System.out.println("(2) Bebida");
                        System.out.println("******************************");
                        System.out.print("Selecciona una opción: ");
                        productType = sc.nextInt();
                        sc.nextLine();
                        while (!(productType < 3 && productType > 0)) {
                            System.out.println("Esa opción no es válida, por favor escribe un número en el rango.");
                            System.out.print("Selecciona una opción: ");
                            productType = sc.nextInt();
                            sc.nextLine();
                        }
                        switch (productType) {
                            case 1:
                                System.out.println("Tipo de alimento:");
                                System.out.println("(1) Caramelo o dulce");
                                System.out.println("(2) Pan");
                                System.out.println("(3) Papas fritas");
                                System.out.println("(4) Sandwich");
                                System.out.println("(5) Otro");
                                System.out.println("******************************");
                                System.out.print("Selecciona una opción: ");
                                type = sc.nextInt();
                                sc.nextLine();
                                while (!(type < 6 && type > 0)) {
                                    System.out.println("Esa opción no es válida, por favor escribe un número en el rango.");
                                    System.out.print("Selecciona una opción: ");
                                    type = sc.nextInt();
                                    sc.nextLine();
                                }
                                break;
                            case 2:
                                System.out.println("Tipo de bebida:");
                                System.out.println("(1) Agua");
                                System.out.println("(2) Jugo");
                                System.out.println("(3) Bebida alcohólica");
                                System.out.println("(4) Refresco");
                                System.out.println("(5) Otro");
                                System.out.println("******************************");
                                System.out.print("Selecciona una opción: ");
                                type = sc.nextInt();
                                sc.nextLine();
                                while (!(type < 6 && type > 0)) {
                                    System.out.println("Esa opción no es válida, por favor escribe un número en el rango.");
                                    System.out.print("Selecciona una opción: ");
                                    type = sc.nextInt();
                                    sc.nextLine();
                                }
                                break;
                            default:
                                break;
                        }
                        System.out.printf("Cantidad de contenido (mililitros o gramos según sea el caso): ");
                        quantityOfContent = sc.nextDouble();
                        sc.nextLine();

                        // Mejor agrega producto por agregacion, para poder hacer product.print. falta inicializar type
                        catalog.addProductToCatalog(upn, name, description, price, quantity, productType, type, quantityOfContent);
                        System.out.println("Producto añadido");
                        Product createdProduct = catalog.getProductByUPN(upn);
                        createdProduct.print();
                        save();
                        break;
                    case 2:
                        System.out.println("*****************************");
                        System.out.println("      Eliminar producto      ");
                        System.out.println("*****************************");
                        System.out.printf("UPN: ");
                        upn = sc.nextLine();
                        while (!(catalog.exists(upn))) {
                            System.out.println("Ese UPN no está registrado. Intenta de nuevo.");
                            System.out.printf("UPN: ");
                            upn = sc.nextLine();
                        }
                        catalog.deleteProductFromCatalog(upn);
                        System.out.println("Producto eliminado");
                        save();
                        break;
                    case 3:
                        System.out.println("*****************************");
                        System.out.println("       Editar producto       ");
                        System.out.println("*****************************");
                        System.out.printf("UPN: ");
                        upn = sc.nextLine();
                        while (!(catalog.exists(upn))) {
                            System.out.println("Ese UPN no está registrado. Intenta de nuevo.");
                            System.out.printf("UPN: ");
                            upn = sc.nextLine();
                        }
                        product = catalog.getProductByUPN(upn);
                        System.out.println("¿Qué deseas editar para este producto?");
                        System.out.println("(1) UPN");
                        System.out.println("(2) Nombre");
                        System.out.println("(3) Descripción");
                        System.out.println("(4) Precio");
                        System.out.println("******************************");
                        System.out.print("Selecciona una opción: ");
                        int selectedOption1 = sc.nextInt();
                        sc.nextLine();
                        boolean wrongOption1 = false;
                        do {
                            if (selectedOption1 < 4 && selectedOption1 > 0) {
                                System.out.println("Selecciona el nuevo valor:");
                            }
                            switch (selectedOption1) {
                                case 1:
                                    System.out.printf("UPN: ");
                                    upn = sc.nextLine();
                                    while (catalog.exists(upn)) {
                                        System.out.println("Ese UPN ya está registrado. Intenta de nuevo.");
                                        System.out.printf("UPN: ");
                                        upn = sc.nextLine();
                                    }
                                    product.setUPN(upn);
                                    System.out.println("UPN actualizado");
                                    break;
                                case 2:
                                    System.out.printf("Nombre: ");
                                    name = sc.nextLine();
                                    product.setName(name);
                                    System.out.println("Nombre actualizado");
                                    break;
                                case 3:
                                    System.out.printf("Descripción: ");
                                    description = sc.nextLine();
                                    product.setDescription(description);
                                    System.out.println("Descripción actualizada");
                                    break;
                                case 4:
                                    System.out.printf("Precio: ");
                                    price = sc.nextInt();
                                    sc.nextLine();
                                    product.setPrice(price);
                                    System.out.println("Precio actualizado");
                                    break;
                                default:
                                    wrongOption1 = true;
                                    System.out.println("Esa opción no es válida, por favor escribe un número en el rango.");
                                    System.out.print("Selecciona una opción: ");
                                    selectedOption1 = sc.nextInt();
                                    sc.nextLine();
                                    break;
                            }
                            save();
                        } while (wrongOption1);
                        break;
                    case 4:
                        catalog.print();
                        break;
                    case 5:
                        break;
                    default:
                        wrongOption = true;
                        System.out.println("Esa opción no es válida, por favor escribe un número en el rango.");
                        System.out.print("Selecciona una opción: ");
                        selectedOption = sc.nextInt();
                        sc.nextLine();
                        break;
                }
            }
        }
    }

    private static void manageUsers(){
        int selectedOption = 0;
        String name;
        String lastname;
        String username;
        String password;

        while(selectedOption != 5) {
            System.out.println("******************************");
            System.out.println("     Administrar usuarios     ");
            System.out.println("******************************");
            System.out.println("(1) Agregar usuario");
            System.out.println("(2) Eliminar usuario");
            System.out.println("(3) Editar usuario");
            System.out.println("(4) Imprimir usuarios");
            System.out.println("(5) Regresar a menú principal");
            System.out.println("******************************");
            System.out.print("Selecciona una opción: ");
            selectedOption = sc.nextInt();
            sc.nextLine();

            boolean wrongOption = true;
            while (wrongOption) {
                switch(selectedOption){
                    case 1:
                        wrongOption = false;
                        System.out.println("******************************");
                        System.out.println("        Agregar usuario       ");
                        System.out.println("******************************");
                        System.out.println("Tipo de usuario:");
                        System.out.println("(1) Administrador");
                        System.out.println("(2) Gerente");
                        System.out.println("(3) Vendedor");
                        System.out.println("******************************");
                        System.out.print("Selecciona una opción: ");
                        int userType = sc.nextInt();
                        sc.nextLine();
                        while (userType > 3 && userType < 1) {
                            System.out.println("Esa opción no es válida, por favor escribe un número en el rango.");
                            System.out.print("Selecciona una opción: ");
                            userType = sc.nextInt();
                            sc.nextLine();
                        }
                        System.out.printf("Nombre(s): ");
                        name = sc.nextLine();
                        System.out.printf("Apellidos: ");
                        lastname = sc.nextLine();
                        System.out.printf("Username: ");
                        username = sc.nextLine();
                        while (userList.exists(username)) {
                            System.out.println("Ese username ya existe, prueba de nuevo.");
                            System.out.printf("Username: ");
                            username = sc.nextLine();
                        }
                        System.out.printf("Contraseña: ");
                        password = sc.nextLine();
                        userList.createUser(username, password, name, lastname, userType);
                        System.out.println("Usuario creado con éxito");
                        save();
                        break;
                    case 2:
                        wrongOption = false;
                        System.out.println("******************************");
                        System.out.println("       Eliminar usuario       ");
                        System.out.println("******************************");
                        System.out.println("Para eliminar un usuario ingresa el username");
                        System.out.printf("Username: ");
                        username = sc.nextLine();
                        while (!userList.exists(username)) {
                            System.out.println("Ese username no existe, prueba de nuevo.");
                            System.out.printf("Username: ");
                            username = sc.nextLine();
                        }
                        userList.deleteUser(username);
                        System.out.println("Usuario eliminado con éxito");
                        save();
                        break;
                    case 3:
                        wrongOption = false;
                        System.out.println("******************************");
                        System.out.println("        Editar usuario        ");
                        System.out.println("******************************");
                        System.out.println("¿Cuál es el username de la usuario que quieres editar?");
                        System.out.printf("Username: ");
                        username = sc.nextLine();
                        while (!userList.exists(username)) {
                            System.out.println("Ese username no existe, prueba de nuevo.");
                            System.out.printf("Username: ");
                            username = sc.nextLine();
                        }
                        User desiredUser = userList.getUserByUsername(username);
                        System.out.println("¿Qué deseas editar para ese usuario?");
                        System.out.println("(1) Username");
                        System.out.println("(2) Contraseña");
                        System.out.println("(3) Nombre(s)");
                        System.out.println("(4) Apellidos");
                        System.out.println("******************************");
                        System.out.print("Selecciona una opción: ");
                        int selectedOption1 = sc.nextInt();
                        sc.nextLine();
                        boolean wrongOption1 = true;
                        while (wrongOption1) {
                            switch (selectedOption1){
                                case 1:
                                    wrongOption1 = false;
                                    System.out.println("¿Cuál es el nuevo valor?");
                                    System.out.print("Username: ");
                                    username = sc.nextLine();
                                    while (userList.exists(username)) {
                                        System.out.println("Ese username ya existe, prueba de nuevo.");
                                        System.out.print("Username: ");
                                        username = sc.nextLine();
                                    }
                                    desiredUser.setUsername(username);
                                    System.out.println("Username actualizado con éxito");
                                    break;
                                case 2:
                                    wrongOption1 = false;
                                    System.out.println("¿Cuál es el nuevo valor?");
                                    System.out.print("Contraseña: ");
                                    password = sc.nextLine();
                                    desiredUser.setPassword(password);
                                    System.out.println("Contraseña actualizada con éxito");
                                    break;
                                case 3:
                                    wrongOption1 = false;
                                    System.out.println("¿Cuál es el nuevo valor?");
                                    System.out.print("Nombre(s): ");
                                    name = sc.nextLine();
                                    desiredUser.setFirstname(name);
                                    System.out.println("Nombre(s) editado(s) con éxito");
                                    break;
                                case 4:
                                    wrongOption1 = false;
                                    System.out.println("¿Cuál es el nuevo valor?");
                                    System.out.print("Apellidos: ");
                                    lastname = sc.nextLine();
                                    desiredUser.setLastname(lastname);
                                    System.out.println("Apellidos editados con éxito");
                                    break;
                                default:
                                    System.out.println("Esa opción no es válida, por favor escribe un número en el rango.");
                                    System.out.print("Selecciona una opción: ");
                                    selectedOption1 = sc.nextInt();
                                    sc.nextLine();
                            }
                            save();
                        }
                        break;
                    case 4:
                        wrongOption = false;
                        System.out.printf("\nUsuarios registrados:\n");
                        userList.print();
                        break;
                    case 5:
                        wrongOption = false;
                        break;
                    default:
                        System.out.println("Esa opción no es válida, por favor escribe un número en el rango.");
                        System.out.print("Selecciona una opción: ");
                        selectedOption = sc.nextInt();
                        sc.nextLine();
                }
            }
        }
    }

    private static void saveAndExit(){
        save();
        System.exit(0);
    }

    private static void save(){
        try {
            catalog.saveCatalog();
            userList.saveUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}