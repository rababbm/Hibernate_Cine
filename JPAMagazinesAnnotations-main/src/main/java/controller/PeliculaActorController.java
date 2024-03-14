package controller;

import java.util.List;
import javax.persistence.*;

import model.Pelicula;
import model.PeliculaActor;
/**
 * La clase PeliculaActorController proporciona métodos para interactuar con las relaciones entre Películas y Actores en la base de datos.
 */
public class PeliculaActorController {
    private EntityManagerFactory entityManagerFactory;
    /**
     * Construye un nuevo PeliculaActorController con la EntityManagerFactory predeterminada.
     */
    public PeliculaActorController() {
        entityManagerFactory = Persistence.createEntityManagerFactory("JPACine");
    }
    /**
     * Construye un nuevo PeliculaActorController con la EntityManagerFactory proporcionada.
     *
     * @param entityManagerFactory La EntityManagerFactory que se utilizará en el PeliculaActorController.
     */
    public PeliculaActorController(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    /**
     * Obtiene todas las relaciones entre películas y actores de la base de datos.
     *
     * @return Una lista de objetos PeliculaActor que representan todas las relaciones entre películas y actores en la base de datos.
     * @throws RuntimeException Si ocurre algún error al obtener las relaciones película-actor.
     */
    public List<PeliculaActor> obtenerTodasPeliculaActores() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            List<PeliculaActor> peliculasActores = em.createQuery("FROM PeliculaActor", PeliculaActor.class)
                    .getResultList();
            for (PeliculaActor peliculaActor : peliculasActores) {
                System.out.println(peliculaActor.toString());
            }
            em.getTransaction().commit();
            return peliculasActores;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error al obtener todas las relaciones pelicula-actor", e);
        } finally {
            em.close();
        }
    }

}
