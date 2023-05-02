package ru.kotikov.library.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Genre {
    private long id;

    private String name;

    @Override
    public String toString() {
        return "- " +
                "id = " + id +
                ", имя = '" + name + '\'';
    }
}
