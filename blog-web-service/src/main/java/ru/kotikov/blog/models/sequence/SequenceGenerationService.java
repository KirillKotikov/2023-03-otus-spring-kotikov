package ru.kotikov.blog.models.sequence;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SequenceGenerationService {

    private final MongoOperations mongoOperations;

    public long getSequenceNumber(String sequenceName) {
        DatabaseSequence counter =
                mongoOperations.findAndModify(
                        Query.query(Criteria.where("_id").is(sequenceName)),
                        new Update().inc("seq", 1),
                        FindAndModifyOptions.options().returnNew(true).upsert(true),
                        DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSequenceNumber() : 1;
    }

}
