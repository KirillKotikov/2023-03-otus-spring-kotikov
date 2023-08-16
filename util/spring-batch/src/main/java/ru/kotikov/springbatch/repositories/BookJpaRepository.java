package ru.kotikov.springbatch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kotikov.springbatch.models.BookJpa;

public interface BookJpaRepository extends JpaRepository<BookJpa, Long> {

    BookJpa findByName(String name);

}
