package ru.kotikov.springbatch.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "books")
public class BookMongo {

    @Id
    private String id;

    private String name;

    @DocumentReference
    private AuthorMongo author;

    @DocumentReference
    private GenreMongo genre;

    public BookMongo(String id, String name, AuthorMongo author, GenreMongo genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public BookMongo(String name, AuthorMongo author, GenreMongo genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public BookMongo setId(String id) {
        this.id = id;
        return this;
    }

}
