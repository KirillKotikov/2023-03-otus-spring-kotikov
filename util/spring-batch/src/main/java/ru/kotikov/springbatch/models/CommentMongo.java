package ru.kotikov.springbatch.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comments")
public class CommentMongo {

    @Id
    private String id;

    @Setter
    private String text;

    @DBRef
    private BookMongo book;

    public CommentMongo(String text, BookMongo book) {
        this.text = text;
        this.book = book;
    }

}
