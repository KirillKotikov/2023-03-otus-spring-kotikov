package ru.kotikov.springbatch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kotikov.springbatch.models.GenreJpa;

public interface GenreJpaRepository extends JpaRepository<GenreJpa, Long> {

    GenreJpa findByName(String name);

}
