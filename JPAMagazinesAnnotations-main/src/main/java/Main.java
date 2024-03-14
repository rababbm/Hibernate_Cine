import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.*;
import model.*;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import view.Menu;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 * La clase Main es la clase principal del programa que gestiona la lógica de negocio y la interacción con el usuario.
 */
public class Main {
    static Scanner scanner= new Scanner(System.in);
    static SessionFactory sessionFactoryObj;
    /**
     * Crea y devuelve una nueva instancia de SessionFactory.
     * @return Una instancia de SessionFactory.
     */
    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml").build();
            Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
            return metadata.getSessionFactoryBuilder().build();
        } catch (HibernateException he) {
            System.out.println("Session Factory creation failure");
            throw he;
        }
    }
    /**
     * Crea y devuelve una nueva instancia de EntityManagerFactory.
     * @return Una instancia de EntityManagerFactory.
     */
    public static EntityManagerFactory createEntityManagerFactory() {
        EntityManagerFactory emf;
        try {
            emf = Persistence.createEntityManagerFactory("JPACine");
        } catch (Throwable ex) {
            System.err.println("Failed to create EntityManagerFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        return emf;
    }
    /**
     * El método main es el punto de entrada del programa.
     * @param args Argumentos de línea de comandos (no se utilizan en este programa).
     */
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = createEntityManagerFactory();
        PeliculaActorController peliculaActorController= new PeliculaActorController(entityManagerFactory);
        PeliculaController peliculaController = new PeliculaController(entityManagerFactory);
        DirectorController directorController = new DirectorController(entityManagerFactory);
        ActorController actorController = new ActorController(entityManagerFactory);
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(entityManagerFactory);
        Menu menu = new Menu();
        int option = menu.mainMenu();
        while (option >= 0 && option < 17) {
            switch (option) {
                case 1:
                    // Llamar al método para poblar masivamente las tablas de la base de datos
                    databaseInitializer.verContenidoTablas();
                    break;
                case 2:
                    directorController.deleteAllDirectors();
                    break;
                case 3:
                    actorController.deleteAllActors();
                    break;
                case 4:
                    peliculaController.deleteAllPeliculas();

                    break;
                case 5:
                    databaseInitializer.deleteAllTables();
                    break;
                case 6:
                    databaseInitializer.dropTables();
                    break;
                case 7:
                    databaseInitializer.createAllTables();
                    break;
                case 8:
                    databaseInitializer.populateAllTables();
                    break;
                case 9:
                    System.out.println("Introduce el año:");
                    int anioBusqueda = scanner.nextInt();
                    List<Pelicula> peliculasEncontradas = peliculaController.buscarPeliculasPorAnio(anioBusqueda);
                    if (peliculasEncontradas.isEmpty()) {
                        System.out.println("No se encontraron películas para el año especificado.");
                    } else {
                        System.out.println("Películas encontradas para el año " + anioBusqueda + ":");
                        for (Pelicula pelicula : peliculasEncontradas) {
                            System.out.println(pelicula.getTitulo() + " (" + pelicula.getAnio() + ")");
                        }
                    }
                    break;
                case 10:
                    System.out.println("Introduce el año:");
                    int aniobus = scanner.nextInt(); // Lee el año proporcionado por el usuario
                    List<Pelicula> peliculasEncontradass = peliculaController.buscarPeliculasAPartirDeAnio(aniobus);
                    if (peliculasEncontradass.isEmpty()) {
                        System.out.println("No se encontraron películas a partir del año indicado");
                    } else {
                        System.out.println("Películas encontradas a partir del año " + aniobus + ":");
                        for (Pelicula pelicula : peliculasEncontradass) {
                            System.out.println(pelicula.getAnio() + " (" + pelicula.getTitulo() + ")");
                        }
                    }
                    break;

                case 11:
                    System.out.println("Introduce el ID de la película:");
                    Long idPelicula = scanner.nextLong(); // Lee el ID proporcionado por el usuario
                    Pelicula peliculaEncontrada = peliculaController.buscarPeliculaPorId(idPelicula);
                    if (peliculaEncontrada != null) {
                        System.out.println("Pelicula encontrada: " + peliculaEncontrada);
                        System.out.println("Introduce el nuevo año para la película:");
                        int nuevoAnio = scanner.nextInt(); // Lee el nuevo año proporcionado por el usuario
                        peliculaController.modificarAnioPelicula(idPelicula, nuevoAnio);
                    } else {
                        System.out.println("No se encontró ninguna película con el ID proporcionado.");
                    }
                    break;
                case 12:
                    System.out.println("Introduce el ID de la película que deseas eliminar:");
                    Long idPeliculaEliminar = scanner.nextLong(); // Lee el ID proporcionado por el usuario
                    peliculaController.eliminarPeliculaPorId(idPeliculaEliminar);
                    break;
                case 13:
                    System.out.println("Introduce el año a partir del cual deseas eliminar las películas:");
                    int anioEliminar = scanner.nextInt(); // Lee el año proporcionado por el usuario
                    peliculaController.eliminarPeliculasPorAnio(anioEliminar);
                    break;

                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
            option = menu.mainMenu();
        }
    }
}