package controller;

import com.opencsv.exceptions.CsvException;
import model.Actor;
import model.Director;
import model.Pelicula;
import com.opencsv.CSVReader;
import model.PeliculaActor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Clase encargada de inicializar la base de datos, crear, poblar y eliminar tablas, y ver el contenido de las tablas.
 */
public class DatabaseInitializer {
    private EntityManagerFactory entityManagerFactory;

    private PeliculaController peliculaController;
    private ActorController actorController;
    private DirectorController directorController;
    private PeliculaActorController peliculaActorController;

    //VER CONTENIDO
    public void verContenidoTablas() {
        System.out.println("CONTENIDO TABLA *****PELICULA******");
        List<Pelicula> peliculas = peliculaController.obtenerTodasPeliculas();
        for (Pelicula pelicula : peliculas) {
            System.out.println(pelicula);
        }
        System.out.println("");
        System.out.println("**********************************");
        System.out.println("");
        System.out.println("CONTENIDO TABLA *****ACTOR******");
        List<Actor> actores = actorController.obtenerTodosActores();
        for (Actor actor : actores) {
            System.out.println(actor);
        }
        System.out.println("");
        System.out.println("**********************************");
        System.out.println("");
        System.out.println("CONTENIDO TABLA *****DIRECTOR******");
        List<Director> directores = directorController.obtenerTodosDirectores();
        for (Director director : directores) {
            System.out.println(director);
        }
        System.out.println("");
        System.out.println("**********************************");
        System.out.println("");

        System.out.println("CONTENIDO TABLA *****PELICULA_ACTOR******");
        List<PeliculaActor> peliculaActores = peliculaActorController.obtenerTodasPeliculaActores();
        for (PeliculaActor peliculaActor : peliculaActores) {
            System.out.println(peliculaActor);
        }
        System.out.println("");
        System.out.println("**********************************");
        System.out.println("");
    }
    /**
     * Método para crear las tablas
     */
    public DatabaseInitializer(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        peliculaController = new PeliculaController();
        actorController = new ActorController();
        directorController = new DirectorController();
        peliculaActorController = new PeliculaActorController();
    }

    public void createAllTables() {
        directorController.crearTablaDirector();
        peliculaController.crearTablaPelicula();
        actorController.crearTablaActor();
        peliculaController.crearTablaPeliculaActor();
    }
    /**
     * Método para eliminar la informacion tablas
     */
    public  void deleteAllTables(){
        peliculaController.deleteAllPeliculas();
        peliculaController.deleteAllPeliculaActor();
        directorController.deleteAllDirectors();
        actorController.deleteAllActors();
    }
    /**
     * Método para eliminar las tablas
     */
    public void dropTables(){

        peliculaController.deletePeliculaTable();
        peliculaController.dropPeliculaActorTable();
        actorController.deleteActorTable();
        directorController.deleteDirectorTable();

    }

    /**
     * Método para poblar las tablas
     */
    public void populateAllTables() {
        // Poblar las tablas de películas, actores y directores
        populatePeliculas();
        populateActores();
        populateDirectores();
        llenarTablaPeliculaActor();
    }
    /**
     * Método privado para poblar la tabla de películas
     */
    private void populatePeliculas() {
        //String csv= "src/main/resources/peliculas.csv";

       // InputStream ctt= DatabaseInitializer.class.getResourceAsStream(csv);

        try (CSVReader reader = new CSVReader(new FileReader("JPAMagazinesAnnotations-main/src/main/resources/peliculas.csv"))) {
            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                if (row.length >= 4) { // Verificar que la fila tenga al menos 4 elementos
                    Pelicula pelicula = new Pelicula();
                    pelicula.setTitulo(row[0]);
                    pelicula.setAnio(Integer.parseInt(row[1]));
                    pelicula.setDuracion(row[2]);
                    pelicula.setEnlace(row[3]);
                    peliculaController.createPelicula(pelicula);

                } else {
                    System.err.println("La fila no tiene el formato esperado: " + Arrays.toString(row));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Datos pelicula poblados correctamente");
    }
    /**
     * Método privado para poblar la tabla de actor
     */
    private void populateActores() {
       // String csv= "src/main/resources/actores.csv";
        //InputStream ctt= DatabaseInitializer.class.getResourceAsStream(csv);

        try (CSVReader reader = new CSVReader(new FileReader("JPAMagazinesAnnotations-main/src/main/resources/actor.csv"))) {
            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                Actor actor = new Actor();
                actor.setNombre(row[0]);
                actor.setEdad(Integer.parseInt(row[1]));
                actorController.createActor(actor);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Datos actor poblados correctamente");
    }
    /**
     * Método privado para poblar la tabla de director
     */
    private void populateDirectores() {
        //String csv= "src/main/resources/directores.csv";
        //String csvv= "JPAMagazinesAnnotations-main/src/main/resources/directores.csv";
        //InputStream ctt= DatabaseInitializer.class.getResourceAsStream(csv);
        try (CSVReader reader = new CSVReader(new FileReader("JPAMagazinesAnnotations-main/src/main/resources/directores.csv"))) {
            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                if (row.length >= 1) {
                    Director director = new Director();
                    director.setNombreDirector(row[0]);
                    directorController.createDirector(director);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Datos director poblados correctamente");
    }
    public DatabaseInitializer() {
        entityManagerFactory = Persistence.createEntityManagerFactory("JPACine");
    }
    /**
     * Método privado para poblar la tabla de PeliculaACtor
     */
    private void llenarTablaPeliculaActor() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            // Ejecutar el script SQL para llenar la tabla PeliculaActor con datos aleatorios
            entityManager.createNativeQuery("INSERT INTO PeliculaActor (pelicula_id, actor_id)\n" +
                    "SELECT\n" +
                    "    p.id AS pelicula_id,\n" +
                    "    (SELECT id FROM Actor ORDER BY RANDOM() LIMIT 1) AS actor_id\n" +
                    "FROM\n" +
                    "    Pelicula AS p;").executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }






}

