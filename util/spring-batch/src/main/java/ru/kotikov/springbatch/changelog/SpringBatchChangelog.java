package ru.kotikov.springbatch.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.kotikov.springbatch.models.AuthorMongo;
import ru.kotikov.springbatch.models.BookMongo;
import ru.kotikov.springbatch.models.CommentMongo;
import ru.kotikov.springbatch.models.GenreMongo;
import ru.kotikov.springbatch.repositories.AuthorMongoRepository;
import ru.kotikov.springbatch.repositories.BookMongoRepository;
import ru.kotikov.springbatch.repositories.CommentMongoRepository;
import ru.kotikov.springbatch.repositories.GenreMongoRepository;

@ChangeLog
public class SpringBatchChangelog {

    private AuthorMongo aladdinAuthor;

    private AuthorMongo cinderellaAuthor;

    private GenreMongo fairyTale;

    private GenreMongo adventure;

    private GenreMongo loveStory;

    private BookMongo aladdin;

    private BookMongo cinderella;


    @ChangeSet(order = "001", id = "dropDb", author = "kotikov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "kotikov")
    public void insertAuthors(AuthorMongoRepository repository) {
        aladdinAuthor = repository.save(new AuthorMongo("1", "Aladdin Author"));
        repository.save(new AuthorMongo("2", "Alice Author"));
        cinderellaAuthor = repository.save(new AuthorMongo("3", "Cinderella Author"));
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "kotikov")
    public void insertGenres(GenreMongoRepository repository) {
        fairyTale = repository.save(new GenreMongo("1", "Fairy tale"));
        adventure = repository.save(new GenreMongo("2", "Adventure"));
        loveStory = repository.save(new GenreMongo("3", "Love story"));
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "kotikov")
    public void insertBooks(BookMongoRepository repository) {
        aladdin = repository.save(new BookMongo("1", "Aladdin", aladdinAuthor, adventure));
        repository.save(new BookMongo("2", "Alice in wonderland", aladdinAuthor, fairyTale));
        cinderella = repository.save(new BookMongo("3", "Cinderella", cinderellaAuthor, loveStory));
    }

    @ChangeSet(order = "005", id = "insertComments", author = "kotikov")
    public void insertComments(CommentMongoRepository repository) {
        repository.save(new CommentMongo("1", "Комментарий к Алладину", aladdin));
        repository.save(new CommentMongo("2", "Второй комментарий к Алладину", aladdin));
        repository.save(new CommentMongo("3", "Коммент к золушке", cinderella));
    }

}
