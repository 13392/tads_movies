package br.grupointegrado.movies.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "movies_actors")
public class MovieActor {

    @EmbeddedId
    private MovieActorPK id;

    @Column(name = "movie_date")
    private LocalDate movieDate;

    public MovieActorPK getId() {
        return id;
    }

    public void setId(MovieActorPK id) {
        this.id = id;
    }

    public LocalDate getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(LocalDate movieDate) {
        this.movieDate = movieDate;
    }
}
