package ru.kotikov.springbatch.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "books")
@NamedEntityGraph(name = "book-genre-author-graph", attributeNodes = {@NamedAttributeNode("genre"),
        @NamedAttributeNode("author")})
public class BookJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private AuthorJpa author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private GenreJpa genre;

    public BookJpa(long id, String name, AuthorJpa author, GenreJpa genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public BookJpa(String name, AuthorJpa author, GenreJpa genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public BookJpa setId(long id) {
        this.id = id;
        return this;
    }

}