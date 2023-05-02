package ru.kotikov.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kotikov.library.dao.AuthorDao;
import ru.kotikov.library.dao.BookDao;
import ru.kotikov.library.dao.GenreDao;
import ru.kotikov.library.models.Author;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Genre;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private static final String BOOK_NOT_FOUND = "Книга с id = %d не найдена!";

    private final BookDao bookDao;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    @Override
    public String countAllBooks() {
        return "Общее количество книг = " + bookDao.count();
    }

    @Override
    public String getAllBooks() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Book book : bookDao.getAll()) {
            stringBuilder.append(book).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public String addBook(String name, long authorId, long genreId) {
        Author author = authorDao.getById(authorId);
        Genre genre = genreDao.getById(genreId);
        Book savedBook = bookDao.insert(new Book(name, author, genre));
        return "Книга {" + savedBook + "} успешно сохранена!";
    }

    @Override
    public String getBookById(long id) {
        Book book = bookDao.getById(id);
        return book == null ? String.format(BOOK_NOT_FOUND, id) : book.toString();
    }

    @Override
    public String updateBook(long id, String name, Long authorId, Long genreId) {
        Author author = authorId == -1 ? null : authorDao.getById(authorId);
        Genre genre = genreId == -1 ? null : genreDao.getById(genreId);
        Book bookForUpdate = new Book(id, name, author, genre);
        Book updatedBook = bookDao.update(bookForUpdate);
        return updatedBook == null ? String.format(BOOK_NOT_FOUND, id) : "Обновлённая книга: " + updatedBook;
    }

    @Override
    public String deleteBookById(long id) {
        return bookDao.deleteById(id) > 0 ? "Книга с id = " + id + " успешно удалена!"
                : String.format(BOOK_NOT_FOUND, id);
    }
}
