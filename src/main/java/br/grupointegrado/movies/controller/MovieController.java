package br.grupointegrado.movies.controller;

import br.grupointegrado.movies.dto.MovieRequestDTO;
import br.grupointegrado.movies.model.Movie;
import br.grupointegrado.movies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieRepository repository;

    @GetMapping
    public List<Movie> findAll() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public Movie findById(@PathVariable Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Filme não foi encontrado"));
    }

    @PostMapping
    public Movie save(@RequestBody MovieRequestDTO dto) {
        Movie movie = new Movie();
        movie.setNome(dto.nome());

        return this.repository.save(movie);
    }

}
