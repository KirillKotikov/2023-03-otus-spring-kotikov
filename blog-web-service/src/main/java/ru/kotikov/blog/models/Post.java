package ru.kotikov.blog.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter
@Setter
@Builder
@Document(collection = "posts")
public class Post {

    @Transient
    public static final String SEQUENCE_NAME = "post_sequence";

    @Id
    private String id;

    private String header;

    private String text;

    @DocumentReference
    private User user;

}
