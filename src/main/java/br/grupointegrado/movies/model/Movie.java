package br.grupointegrado.movies.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity // permite que a classe seja gerenciada pelo JPA
@Table // representa que a classe vai ser persistida em uma tabela
public class Movie {

    @Id // representa a nossa PK (primary key)
    // indica que esse atributo é auto_increment no bd
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name") // esse é óbvio
    private String nome;

    @Column
    private BigDecimal nota;

    @Column
    private String resumo;

    // representa que esse lado é o lado forte da relação
    // esse é o lado que carrega a FK
    // esse lado do mapeamento é obrigatório
    @ManyToOne
    // indica a coluna que é feito o join no bd
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    // indicador para não acontecer um loop infinito
    @JsonIgnoreProperties("movies")
    private Category category;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieActor> actors;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public List<MovieActor> getActors() {
        return actors;
    }

    public void setActors(List<MovieActor> actors) {
        this.actors = actors;
    }

    // encapsulamento de regras
    public void addActor(MovieActor movieActor) {
        if (this.actors == null) {
            this.actors = new ArrayList<>();
        }
        this.actors.add(movieActor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
