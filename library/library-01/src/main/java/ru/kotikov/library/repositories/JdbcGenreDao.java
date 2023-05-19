package ru.kotikov.library.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.kotikov.library.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class JdbcGenreDao implements GenreDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public JdbcGenreDao(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcOperations.getJdbcOperations()
                .query("""
                            SELECT id, name
                            FROM genres
                        """, new GenreMapper());
    }

    @Override
    public Genre getById(long id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        try {
            return namedParameterJdbcOperations.queryForObject(
                    """
                            SELECT id, name 
                            FROM genres 
                            WHERE id = :id
                            """, mapSqlParameterSource, new GenreMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
