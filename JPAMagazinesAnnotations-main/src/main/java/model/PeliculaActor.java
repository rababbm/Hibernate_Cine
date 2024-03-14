package model;

import model.Actor;
import model.Pelicula;

import javax.persistence.*;
/**
 * La clase PeliculaActor representa la relación entre una película y un actor en el sistema.
 */
@Entity
@Table(name = "PeliculaActor")
public class PeliculaActor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pelicula_id")
    private Pelicula pelicula;

    @ManyToOne
    @JoinColumn(name = "actor_id")
    private Actor actor;


    public PeliculaActor() {
    }

    @Override
    public String toString() {
        return "PeliculaActor{" +
                "id=" + id +
                ", pelicula=" + pelicula.getTitulo() +
                ", actor=" + actor.getNombre() +
                '}';
    }

    /**
     * Construye un nuevo objeto PeliculaActor.
     * @param pelicula La película asociada a la relación.
     * @param actor El actor asociado a la relación.
     */
    public PeliculaActor(Pelicula pelicula, Actor actor) {
        this.pelicula = pelicula;
        this.actor = actor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
}
