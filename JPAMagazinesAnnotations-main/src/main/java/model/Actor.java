package model;

import javax.persistence.*;
import java.util.List;

/**
 * La clase Actor representa a un actor en el sistema.
 */
@Entity
@Table(name = "Actor")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "edad")
    private int edad;

    // Relación con Pelicula (muchos a muchos)
    @ManyToMany
    @JoinTable(
            name = "PeliculaActor",
            joinColumns = @JoinColumn(name = "actor_id"),
            inverseJoinColumns = @JoinColumn(name = "pelicula_id")
    )
    private List<Pelicula> peliculas;

    /**
     * Construye un nuevo objeto Actor.
     * @param id El identificador del actor.
     * @param nombre El nombre del actor.
     * @param edad La edad del actor.
     * @param peliculas La lista de películas en las que el actor ha participado.
     */

    public Actor(Long id, String nombre, int edad, List<Pelicula> peliculas) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.peliculas = peliculas;
    }
    /**
     * Construye un nuevo objeto Actor sin inicializar sus atributos.
     */
    public Actor (){
        //constructor vacio
    }

    @Override
    public String toString() {
        return String.format("| %-4d | %-20s | %-4d |", id, nombre, edad);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
    /**
     * Obtiene la lista de películas en las que el actor ha participado.
     * @return La lista de películas en las que el actor ha participado
     */
    public List<Pelicula> getPeliculas() {
        return peliculas;
    }
    /**
     * Establece la lista de películas en las que el actor ha participado.
     * @param peliculas La lista de películas en las que el actor ha participado.
     */
    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }
}
