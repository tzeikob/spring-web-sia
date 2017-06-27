package com.app.db;

import com.app.exc.DokumentNotFoundException;
import com.app.model.Dokument;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class DokumentRepository implements CollectionRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Dokument> findAll() {
        List<Dokument> dokuments = mongoTemplate.findAll(Dokument.class);

        return dokuments;
    }

    @Override
    public Dokument findByTitle(String title) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(title));

        Dokument dokument = mongoTemplate.findOne(query, Dokument.class);
        
        if(dokument == null) {
            throw new DokumentNotFoundException(title);
        }

        return dokument;
    }

    @Override
    public List<Dokument> findByAuthor(String author) {
        Query query = new Query();
        query.addCriteria(Criteria.where("author").is(author));

        List<Dokument> dokuments = mongoTemplate.find(query, Dokument.class);

        return dokuments;
    }

    @Override
    public Dokument save(Dokument dokument) {
        mongoTemplate.save(dokument);

        return dokument;
    }

    @Override
    public void delete(String id) {
        Dokument dok = mongoTemplate.findById(id, Dokument.class);

        mongoTemplate.remove(dok);
    }
}
