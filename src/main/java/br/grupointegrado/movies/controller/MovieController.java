package br.grupointegrado.movies.controller;

import br.grupointegrado.movies.dto.MovieRequestDTO;
import br.grupointegrado.movies.model.Actor;
import br.grupointegrado.movies.model.Movie;
import br.grupointegrado.movies.model.MovieActor;
import br.grupointegrado.movies.model.MovieActorPK;
import br.grupointegrado.movies.repository.ActorRepository;
import br.grupointegrado.movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private ActorRepository actorRepository;

    @GetMapping
    public ResponseEntity<List<Movie>> findAll() {
        List<Movie> movies = this.repository.findAll();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public Movie findById(@PathVariable Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Filme não foi encontrado"));
    }

    @PostMapping
    public ResponseEntity<Movie> save(@RequestBody MovieRequestDTO dto) {
        if (dto.nome().isEmpty()) {
            return ResponseEntity.status(428).build();
        }

        Movie movie = new Movie();
        movie.setNome(dto.nome());

        this.repository.save(movie);
        return ResponseEntity.ok(movie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Movie movie = this.repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Filme não foi encontrado"));

        this.repository.delete(movie);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@PathVariable Integer id, @RequestBody MovieRequestDTO dto) {
        if (dto.nome().isEmpty()) {
            return ResponseEntity.status(428).build();
        }

        Movie movie = this.repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Filme não foi encontrado"));

        movie.setNome(dto.nome());

        this.repository.save(movie);
        return ResponseEntity.ok(movie);
    }

    @PostMapping("/{id}/nota")
    public ResponseEntity<Movie> addNota(@PathVariable Integer id,
                         @RequestBody BigDecimal nota) {

        Movie movie = this.repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Filme não foi encontrado"));

        movie.setNota(nota);

        this.repository.save(movie);
        return ResponseEntity.ok(movie);
    }

    @PostMapping("/{id}/resumo")
    public ResponseEntity<Movie> addResumo(@PathVariable Integer id,
                                         @RequestBody String resumo) {

        Movie movie = this.repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Filme não foi encontrado"));

        movie.setResumo(resumo);

        this.repository.save(movie);
        return ResponseEntity.ok(movie);
    }

    @PostMapping("/{id}/add-actor")
    public ResponseEntity<Movie> addActor(@PathVariable Integer id,
                                           @RequestBody Integer actorId) {

        Movie movie = repository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        Actor actor = actorRepository.findById(actorId).orElseThrow(() -> new RuntimeException("Actor not found"));

        MovieActorPK pk = new MovieActorPK();
        pk.setMovieId(movie.getId());
        pk.setActorId(actor.getId());

        MovieActor movieActor = new MovieActor();
        movieActor.setId(pk);
        movieActor.setActor(actor);
        movieActor.setMovie(movie);
        movieActor.setMovieDate(LocalDate.now());

        movie.addActor(movieActor);

        repository.save(movie); // salva o filme e a relação via cascade

        return ResponseEntity.ok(movie);

    }

}
