package ru.kotikov.library.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.kotikov.library.models.Author;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Comment;
import ru.kotikov.library.models.Genre;
import ru.kotikov.library.repositories.AuthorRepository;
import ru.kotikov.library.repositories.BookRepository;
import ru.kotikov.library.repositories.CommentRepository;
import ru.kotikov.library.repositories.GenreRepository;

@ChangeLog
public class LibraryChangelog {

    private Author aladdinAuthor;

    private Author aliceAuthor;

    private Author cinderellaAuthor;

    private Genre fairyTale;

    private Genre adventure;

    private Genre loveStory;

    private Book aladdin;

    private Book cinderella;


    @ChangeSet(order = "001", id = "dropDb", author = "kotikov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "kotikov")
    public void insertAuthors(AuthorRepository repository) {
        aladdinAuthor = repository.save(new Author("1", "Aladdin Author")).block();
        aliceAuthor = repository.save(new Author("2", "Alice Author")).block();
        cinderellaAuthor = repository.save(new Author("3", "Cinderella Author")).block();
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "kotikov")
    public void insertGenres(GenreRepository repository) {
        fairyTale = repository.save(new Genre("1", "Fairy tale")).block();
        adventure = repository.save(new Genre("2", "Adventure")).block();
        loveStory = repository.save(new Genre("3", "Love story")).block();
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "kotikov")
    public void insertBooks(BookRepository repository) {
        aladdin = repository.save(new Book("1", "Aladdin", aladdinAuthor, adventure)).block();
        repository.save(new Book("2", "Alice in wonderland", aliceAuthor, fairyTale)).block();
        cinderella = repository.save(new Book("3", "Cinderella", cinderellaAuthor, loveStory)).block();
    }

    @ChangeSet(order = "005", id = "insertComments", author = "kotikov")
    public void insertComments(CommentRepository repository) {
        repository.save(new Comment("1", "Комментарий к Алладину", aladdin)).block();
        repository.save(new Comment("2", "Второй комментарий к Алладину", aladdin)).block();
        repository.save(new Comment("3", "Коммент к золушке", cinderella)).block();
    }

}
