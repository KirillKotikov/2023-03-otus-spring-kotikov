package ru.kotikov.library.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.kotikov.library.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class JdbcAuthorDao implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public JdbcAuthorDao(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcOperations.getJdbcOperations()
                .query("""
                            SELECT id, name
                            FROM authors
                        """, new AuthorMapper());
    }

    @Override
    public Author getById(long id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        try {
            return namedParameterJdbcOperations.queryForObject(
                    """
                            SELECT id, name 
                            FROM authors 
                            WHERE id = :id
                            """, mapSqlParameterSource, new AuthorMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }
}
