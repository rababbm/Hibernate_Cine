-- Crear bbdd
CREATE DATABASE hibernate;

-- Crear las tablas de las clases:
CREATE TABLE Director (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255)
);

CREATE TABLE Pelicula (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255),
    año INTEGER,
    duracion VARCHAR(10),
    enlace VARCHAR(7000),
    director_id INTEGER REFERENCES Director(id) -- La columna director_id hace referencia al director de la película
);

CREATE TABLE Actor (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255),
    edad INTEGER,
    genero VARCHAR(255)
);

-- Tabla intermedia para la relación muchos a muchos entre Pelicula y Actor
CREATE TABLE PeliculaActor (
    id SERIAL PRIMARY KEY,
    pelicula_id INTEGER REFERENCES Pelicula(id),
    actor_id INTEGER REFERENCES Actor(id),
);
-- Insertar datos aleatorios en la tabla PeliculaActor
INSERT INTO PeliculaActor (pelicula_id, actor_id)
SELECT
    p.id AS pelicula_id,
    a.id AS actor_id
FROM
    (SELECT id FROM Pelicula ORDER BY RANDOM() LIMIT 52) AS p
CROSS JOIN LATERAL
    (SELECT id FROM Actor ORDER BY RANDOM() LIMIT 1) AS a;

