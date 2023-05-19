package ru.kotikov.library.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    public JpaCommentRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Comment> findAll() {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c join fetch c.book", Comment.class);
        return query.getResultList();
    }

    @Override
    public List<Comment> findByBook(Book book) {
        TypedQuery<Comment> query
                = em.createQuery("select c from Comment c join fetch c.book where c.book = :book", Comment.class);
        query.setParameter("book", book);
        return query.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() <= 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public int deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Comment c " +
                "where c.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }
}
