package ru.kotikov.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kotikov.library.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
