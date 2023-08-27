package ru.kotikov.springbatch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kotikov.springbatch.models.AuthorJpa;

public interface AuthorJpaRepository extends JpaRepository<AuthorJpa, Long> {

    AuthorJpa findByName(String name);

}
