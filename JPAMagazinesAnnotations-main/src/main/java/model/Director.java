package model;

import javax.persistence.*;
import java.util.List;

/**
 * La clase Director representa a un director de películas en el sistema.
 */
@Entity
@Table(name = "Director")
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombreDirector;

    // Relación con Pelicula (uno a muchos)
    @OneToMany(mappedBy = "director")
    private List<Pelicula> peliculas;

    // Constructor, getters y setters
    @Override
    public String toString() {
        return String.format("| %-4d | %-50s |", id, nombreDirector);
    }
    /**
     * Construye un nuevo objeto Director.
     * @param id El identificador del director.
     * @param nombreDirector El nombre del director.
     * @param peliculas La lista de películas dirigidas por el director.
     */
    public Director(Long id, String nombreDirector, List<Pelicula> peliculas) {
        this.id = id;
        this.nombreDirector = nombreDirector;
        this.peliculas = peliculas;
    }
    public  Director(){

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreDirector() {
        return nombreDirector;
    }

    public void setNombreDirector(String nombreDirector) {
        this.nombreDirector = nombreDirector;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }
}
