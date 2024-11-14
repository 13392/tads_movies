package br.grupointegrado.movies.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class MovieActorPK {

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    @JsonIgnoreProperties("actors")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "actor_id", referencedColumnName = "id")
    private Actor actor;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
}
