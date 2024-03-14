package controller;
import model.Director;
import model.Pelicula;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * La clase DirectorController proporciona métodos para interactuar con entidades Director en la base de datos.
 */
public class DirectorController {

    private EntityManagerFactory entityManagerFactory;
    /**
     * Construye un nuevo DirectorController con la EntityManagerFactory predeterminada.
     */
    public DirectorController() {
        entityManagerFactory = Persistence.createEntityManagerFactory("JPACine");
    }
    /**
     * Crea una nueva entidad de director en la base de datos.
     *
     * @param director El objeto Director que se creará en la base de datos.
     */
    public void createDirector(Director director) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(director);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Elimina todas las entidades de director de la base de datos.
     */
    public void deleteAllDirectors() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Director").executeUpdate();
        em.getTransaction().commit();
        em.close();
        System.out.println("-¡Informacion borrada!-");
    }
    /**
     * Crea la tabla Director en la base de datos.
     */
    public void crearTablaDirector() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            // Ejecutar la consulta de creación de tabla
            entityManager.createNativeQuery("CREATE TABLE IF NOT EXISTS Director (id SERIAL PRIMARY KEY, nombre VARCHAR(255))").executeUpdate();

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

    public DirectorController(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    /**
     * Elimina la tabla Director de la base de datos.
     */

    public void deleteDirectorTable() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.createNativeQuery("DROP TABLE IF EXISTS Director CASCADE").executeUpdate();
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
     * Recupera todas las entidades de director de la base de datos.
     *
     * @return Una lista de objetos Director que representan a todos los directores en la base de datos.
     */
    public List<Director> obtenerTodosDirectores() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Director> directores = null;
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            TypedQuery<Director> query = entityManager.createQuery("SELECT d FROM Director d", Director.class);
            directores = query.getResultList();
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return directores;
    }

}