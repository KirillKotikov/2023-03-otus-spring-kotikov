package ru.kotikov.docker.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.kotikov.docker.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
