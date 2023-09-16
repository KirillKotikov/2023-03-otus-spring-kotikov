package ru.kotikov.docker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kotikov.docker.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
