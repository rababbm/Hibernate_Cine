package controller;
import model.Pelicula;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * La clase PeliculaController proporciona métodos para interactuar con las películas en la base de datos.
 */
public class PeliculaController {

    private EntityManagerFactory entityManagerFactory;
    /**
     * Construye un nuevo PeliculaController con la EntityManagerFactory predeterminada.
     */
    public PeliculaController() {
        entityManagerFactory = Persistence.createEntityManagerFactory("JPACine");
    }
    /**
     * Construye un nuevo PeliculaController con la EntityManagerFactory proporcionada.
     *
     * @param entityManagerFactory La EntityManagerFactory que se utilizará en el PeliculaController.
     */
    /**
     * Construye un nuevo PeliculaController con la EntityManagerFactory proporcionada.
     *
     * @param entityManagerFactory La EntityManagerFactory que se utilizará en el PeliculaController.
     */
    public PeliculaController(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    public void createPelicula(Pelicula pelicula) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(pelicula);
        em.getTransaction().commit();
        em.close();
    }
    // Método para borrar todas las películas
    public void deleteAllPeliculas() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Pelicula").executeUpdate();
        em.getTransaction().commit();
        em.close();
        System.out.println("-¡Informacion borrada!-");
    }


    public void crearTablaPelicula() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            // Ejecutar la consulta de creación de tabla
            entityManager.createNativeQuery("CREATE TABLE IF NOT EXISTS Pelicula (id SERIAL PRIMARY KEY, titulo VARCHAR(255), anio INTEGER, duracion VARCHAR(50), enlace VARCHAR(255),director_id INTEGER REFERENCES Director(id))").executeUpdate();

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



    /**
     * Elimina todas las películas de la base de datos.
     */
    public void deletePeliculaTable() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.createNativeQuery("DROP TABLE IF EXISTS Pelicula CASCADE").executeUpdate();
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

    /**
     * Obtiene todas las películas de la base de datos.
     *
     * @return Una lista de todas las películas en la base de datos.
     */
    public List<Pelicula> obtenerTodasPeliculas() {

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Pelicula> peliculas = em.createQuery("from Pelicula", Pelicula.class)
                .getResultList();
        for (Pelicula pelicula : peliculas) {
            System.out.println(pelicula.toString());
        }
        em.getTransaction().commit();
        em.close();

        return peliculas;
    }
    //TABLA INTERMEDIA ===================================
    //delete informacion
    public void deleteAllPeliculaActor() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM PeliculaActor").executeUpdate();
        em.getTransaction().commit();
        em.close();
        System.out.println("-¡Relaciones PeliculaActor borradas!-");
    }
    //drop table
    public void dropPeliculaActorTable() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.createNativeQuery("DROP TABLE IF EXISTS PeliculaActor CASCADE").executeUpdate();
            transaction.commit();
            System.out.println("Tabla PeliculaActor eliminada exitosamente.");
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
    public void crearTablaPeliculaActor() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            // Ejecutar la consulta de creación de tabla
            entityManager.createNativeQuery("CREATE TABLE IF NOT EXISTS PeliculaActor (id SERIAL PRIMARY KEY, pelicula_id INTEGER REFERENCES Pelicula(id), actor_id INTEGER REFERENCES Actor(id))").executeUpdate();

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

    public List<Pelicula> buscarPeliculasPorAnio(int anio) {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<Pelicula> peliculas = null;

        try {
            String jpql = "SELECT p FROM Pelicula p WHERE p.anio = :anio";
            TypedQuery<Pelicula> query = em.createQuery(jpql, Pelicula.class);
            query.setParameter("anio", anio);
            peliculas = query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return peliculas;
    }
    //BUscar pelicula a partir de un año concreto
    public List<Pelicula> buscarPeliculasAPartirDeAnio(int anio) {
        EntityManager em = entityManagerFactory.createEntityManager();
        String jpql = "SELECT p FROM Pelicula p WHERE p.anio >= :anio";
        TypedQuery<Pelicula> query = em.createQuery(jpql, Pelicula.class);
        query.setParameter("anio", anio);
        List<Pelicula> peliculas = query.getResultList();
        em.close(); // Cerrar el EntityManager
        return peliculas;
    }
    //MOdificar pelicula a partir de su id + buscar por id
    public Pelicula buscarPeliculaPorId(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Pelicula pelicula = em.find(Pelicula.class, id);
        em.close(); // Cerrar el EntityManager
        return pelicula;
    }
    public void modificarAnioPelicula(Long id, int nuevoAnio) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Pelicula pelicula = em.find(Pelicula.class, id);
            if (pelicula != null) {
                pelicula.setAnio(nuevoAnio);
                em.merge(pelicula);
                transaction.commit();
                System.out.println("Año de la película actualizado correctamente.");
            } else {
                System.out.println("No se encontró ninguna película con el ID proporcionado.");
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    //metodo para eliminar una pelicula por si id:
    public void eliminarPeliculaPorId(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Pelicula pelicula = em.find(Pelicula.class, id);
            if (pelicula != null) {
                em.remove(pelicula);
                transaction.commit();
                System.out.println("Pelicula eliminada correctamente.");
            } else {
                System.out.println("No se encontró ninguna película con el ID proporcionado.");
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    //metodo para eliminar una peliculas a partir de un año:
    public List<Pelicula> eliminarPeliculasPorAnio(int anio) {
        List<Pelicula> peliculasEliminadas = new ArrayList<>();
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            List<Pelicula> peliculas = em.createQuery("SELECT p FROM Pelicula p WHERE p.anio >= :anio", Pelicula.class)
                    .setParameter("anio", anio)
                    .getResultList();
            for (Pelicula pelicula : peliculas) {
                peliculasEliminadas.add(pelicula);
                em.remove(pelicula);
            }
            transaction.commit();
            System.out.println("Películas a partir del año " + anio + " eliminadas correctamente:");
            for (Pelicula pelicula : peliculasEliminadas) {
                System.out.println(pelicula);
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
        return peliculasEliminadas;
    }
}
