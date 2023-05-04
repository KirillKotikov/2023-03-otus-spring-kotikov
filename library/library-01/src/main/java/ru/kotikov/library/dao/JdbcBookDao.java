package ru.kotikov.library.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.kotikov.library.models.Author;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
public class JdbcBookDao implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public JdbcBookDao(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        Integer count = namedParameterJdbcOperations.getJdbcOperations()
                .queryForObject("SELECT COUNT(*) FROM books", Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.getJdbcOperations()
                .query("""
                            SELECT b.id, b.name, author_id, a.name AS author_name, genre_id, g.name AS genre_name
                            FROM books AS b
                            LEFT JOIN authors a ON b.author_id = a.id
                            LEFT JOIN genres g ON b.genre_id = g.id
                        """, new BookMapper());
    }

    @Override
    public Book insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", book.getName());
        mapSqlParameterSource.addValue("author_id", book.getAuthor().getId());
        mapSqlParameterSource.addValue("genre_id", book.getGenre().getId());

        namedParameterJdbcOperations.update(
                """
                        INSERT INTO books (name, author_id, genre_id)
                        VALUES (:name, :author_id, :genre_id )
                        """, mapSqlParameterSource, keyHolder
        );
        return book.setId((Long) keyHolder.getKey());
    }

    @Override
    public Book getById(long id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        try {
            return namedParameterJdbcOperations.queryForObject(
                    """
                            SELECT b.id, b.name, author_id, a.name AS author_name, genre_id, g.name AS genre_name
                            FROM books AS b
                            LEFT JOIN authors a ON b.author_id = a.id
                            LEFT JOIN genres g ON b.genre_id = g.id
                            WHERE b.id = :id
                            """, mapSqlParameterSource, new BookMapper());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public Book update(Book updateBook) {
        Book oldBook = getById(updateBook.getId());
        if (oldBook == null) {
            return null;
        }
        long id = updateBook.getId();
        String bookName = updateBook.getName().isBlank() ? oldBook.getName() : updateBook.getName();
        Author author = updateBook.getAuthor() == null ? oldBook.getAuthor() : updateBook.getAuthor();
        Genre genre = updateBook.getGenre() == null ? oldBook.getGenre() : updateBook.getGenre();
        Map<String, String> params = Map.of(
                "id", String.valueOf(id),
                "name", bookName,
                "author_id", String.valueOf(author.getId()),
                "genre_id", String.valueOf(genre.getId())
        );
        namedParameterJdbcOperations.update(
                """
                        UPDATE books 
                        SET name = :name, author_id = :author_id, genre_id = :genre_id 
                        WHERE id = :id;
                        """, params
        );
        return new Book(id, bookName, author, genre);
    }

    @Override
    public int deleteById(long id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        return namedParameterJdbcOperations.update("DELETE FROM books WHERE id = :id", mapSqlParameterSource);
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            long authorId = resultSet.getLong("author_id");
            String authorName = resultSet.getString("author_name");
            long genreId = resultSet.getLong("genre_id");
            String genreName = resultSet.getString("genre_name");
            return new Book(id, name, new Author(authorId, authorName), new Genre(genreId, genreName));
        }
    }
}
