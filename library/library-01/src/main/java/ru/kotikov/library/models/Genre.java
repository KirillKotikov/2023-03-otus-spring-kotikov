package ru.kotikov.library.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class Genre extends AbstractLibraryModel{
    private long id;

    private String name;

    @Override
    public String toString() {
        return "- " +
                "id = " + id +
                ", имя = '" + name + '\'';
    }
}
