package model;
import javax.persistence.*;
import java.util.List;
/**
 * La clase Pelicula representa una película en el sistema.
 */
@Entity
@Table(name = "Pelicula")
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "anio")
    private int anio;

    @Column(name = "duracion")
    private String duracion;

    @Column(name = "enlace")
    private String enlace;

    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;

    @ManyToMany(mappedBy = "peliculas")
    private List<Actor> actores;

    // Constructor, getters y setters


    @Override
    public String toString() {
        return String.format("| %-4d | %-50s | %-4d | %-10s | %-70s |", id, titulo, anio, duracion, enlace);
    }
    /**
     * Construye un nuevo objeto Pelicula.
     * @param id El identificador de la película.
     * @param titulo El título de la película.
     * @param anio El año de lanzamiento de la película.
     * @param duracion La duración de la película.
     * @param enlace El enlace de la película.
     * @param director El director de la película.
     * @param actores La lista de actores que participan en la película.
     */

    public Pelicula(Long id, String titulo, int anio, String duracion, String enlace, Director director, List<Actor> actores) {
        this.id = id;
        this.titulo = titulo;
        this.anio = anio;
        this.duracion = duracion;
        this.enlace = enlace;
        this.director = director;
        this.actores = actores;
    }
    public Pelicula() {
        // Constructor vacío
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public List<Actor> getActores() {
        return actores;
    }

    public void setActores(List<Actor> actores) {
        this.actores = actores;
    }
}
