package ru.kotikov.library.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.kotikov.library.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
