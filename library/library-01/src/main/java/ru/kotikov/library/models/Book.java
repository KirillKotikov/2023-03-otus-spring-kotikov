package ru.kotikov.library.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Book {
    private long id;

    private String name;

    private Author author;

    private Genre genre;

    public Book(String name, Author author, Genre genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public Book setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "- " +
                "id = " + id +
                ", название = '" + name + '\'' +
                ", автор = " + author.getName() +
                ", жанр = " + genre.getName();
    }
}
