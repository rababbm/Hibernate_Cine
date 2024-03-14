package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * La clase Menu proporciona un menú interactivo para que el usuario realice diferentes acciones en el sistema.
 */
public class Menu {
    private int option;
    /**
     * Crea un nuevo objeto Menu.
     */
    public Menu() {
        super();
    }
    /**
     * Muestra el menú principal y permite al usuario seleccionar una opción.
     * @return La opción seleccionada por el usuario.
     */
    public int mainMenu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        do {

            System.out.println(" \nMENU PRINCIPAL \n");

            System.out.println("1. Ver contenido de las tablas");
            System.out.println("2. Borrar informacion de Director");
            System.out.println("3. Borrar informacion de Actor");
            System.out.println("4. Borrar informacion de Pelicula");
            System.out.println("5. Borrar toda la informacion de las tablas");
            System.out.println("6. Borrar las tablas de la BD");
            System.out.println("7. Crear las tablas en la base de datos");
            System.out.println("8. Poblar masivamente las tablas");
            System.out.println("9. Buscar pelicula por año concreto");
            System.out.println("10. Buscar peliculas a partir de un año concreto");
            System.out.println("11. Modificar el año de una pelicula (Id)");
            System.out.println("12. Eliminar una pelicula (Id)");
            System.out.println("13. Eliminar peliculas a partir de un año concreto");
            System.out.println("0. Sortir. ");

            System.out.println("Escull opció: ");
            try {
                option = Integer.parseInt(br.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println("valor no vàlid");
                e.printStackTrace();
            }
        } while (option != 0 && option != 1 && option != 2 && option != 3 && option != 4 && option != 5 && option != 6 && option != 7
                && option != 8 && option != 9 && option != 10 && option != 11 && option != 12 && option != 13 && option != 14 && option != 15);
        return option;
    }
}