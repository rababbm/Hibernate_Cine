package controller;

import model.Actor;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Controlador para la gestión de actores en la base de datos.
 */
public class ActorController {

    private EntityManagerFactory entityManagerFactory;
    /**
     * Constructor por defecto que inicializa el EntityManagerFactory.
     */
    public ActorController() {
        entityManagerFactory = Persistence.createEntityManagerFactory("JPACine");
    }
    /**
     * Método para crear un nuevo actor en la base de datos.
     *
     * @param actor El actor a crear.
     */
    public void createActor(Actor actor) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(actor);
        em.getTransaction().commit();
        em.close();
    }


    /**
     * Método para borrar todos los actores de la base de datos.
     */
    public void deleteAllActors() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Actor").executeUpdate();
        em.getTransaction().commit();
        em.close();
        System.out.println("-¡Informacion borrada!-");
    }
    /**
     * Método para crear la tabla de actores en la base de datos.
     */
    public void crearTablaActor() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            // Ejecutar la consulta de creación de tabla
            entityManager.createNativeQuery("CREATE TABLE IF NOT EXISTS Actor (id SERIAL PRIMARY KEY, nombre VARCHAR(255), edad INTEGER, genero VARCHAR(255))").executeUpdate();

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
    public ActorController(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Método para eliminar la tabla de actores de la base de datos.
     */
    public void deleteActorTable() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.createNativeQuery("DROP TABLE IF EXISTS Actor CASCADE").executeUpdate();
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
     * Método para obtener todos los actores de la base de datos.
     *
     * @return Lista de todos los actores en la base de datos.
     */
    public List<Actor> obtenerTodosActores() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Actor> actores = null;
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            TypedQuery<Actor> query = entityManager.createQuery("SELECT a FROM Actor a", Actor.class);
            actores = query.getResultList();
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
        return actores;
    }


}
