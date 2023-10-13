package ru.kotikov.blog.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.kotikov.blog.models.Comment;
import ru.kotikov.blog.models.Post;
import ru.kotikov.blog.models.User;
import ru.kotikov.blog.repositories.CommentRepository;
import ru.kotikov.blog.repositories.PostRepository;
import ru.kotikov.blog.repositories.UserRepository;

import java.util.Arrays;
import java.util.List;

@ChangeLog
public class BlogChangelog {

    private User admin;

    private User kotikov;

    private Post firstPost;

    private Post secondPost;

    @ChangeSet(order = "001", id = "dropDb", author = "kotikov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertUsers", author = "kotikov")
    public void insertUsers(UserRepository repository) {
        admin = new User("Admin", "admin", "admin".toCharArray(),
                List.of(
                        new SimpleGrantedAuthority("ROLE_ADMIN"),
                        new SimpleGrantedAuthority("ROLE_USER")
                ));
        kotikov = new User("Кирилл Котиков", "kirill", "1234".toCharArray(),
                List.of(
                        new SimpleGrantedAuthority("ROLE_USER")
                ));
        User user = new User("user", "user", "user".toCharArray(),
                List.of(
                        new SimpleGrantedAuthority("ROLE_USER")
                ));
        repository.saveAll(Arrays.asList(admin, kotikov, user));
    }

    @ChangeSet(order = "003", id = "insertPostList", author = "kotikov")
    public void insertPostList(PostRepository repository) {
        firstPost = Post.builder()
                .user(admin)
                .header("Мы рады Вас приветствовать!")
                .text("Оставлять посты и комментировать могут только зарегистрированные пользователи! " +
                        "Приятного чтения:)")
                .build();
        secondPost = Post.builder()
                .user(kotikov)
                .header("Русский аналог Lorem Ipsum")
                .text("Прародителем текста-рыбы является известный \"Lorem Ipsum\" — латинский текст, " +
                        "ноги которого растут аж из 45 года до нашей эры. Сервисов по созданию случайного " +
                        "текста на основе Lorem Ipsum великое множество, однако все они имеют один существенный " +
                        "недостаток: их \"рыба текст\" подходит лишь для англоязычных ресурсов/проектов. Мы же, " +
                        "фактически, предлагаем Lorem Ipsum на русском языке — вы можете использовать полученный " +
                        "здесь контент абсолютно бесплатно и в любых целях, не запрещённых законодательством. " +
                        "Однако в случае, если сгенерированный здесь текст используется в коммерческом или " +
                        "публичном проекте, ссылка на наш сервис обязательна.")
                .build();
        repository.saveAll(Arrays.asList(firstPost, secondPost));
    }

    @ChangeSet(order = "004", id = "insertCommentList", author = "kotikov")
    public void insertCommentList(CommentRepository repository) {
        Comment firstComment = Comment.builder()
                .post(firstPost)
                .user(kotikov)
                .text("Очень важно соблюдать правила, я за!")
                .build();
        Comment secondComment = Comment.builder()
                .post(firstPost)
                .user(kotikov)
                .text("А можно и мне стать администратором? :)")
                .build();
        Comment thirdComment = Comment.builder()
                .post(firstPost)
                .user(admin)
                .text("Спасибо! Админом сделать не могу :)")
                .build();
        repository.saveAll(Arrays.asList(firstComment, secondComment, thirdComment));
    }

}
