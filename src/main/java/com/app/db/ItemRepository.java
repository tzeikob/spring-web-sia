package com.app.db;

import com.app.model.Item;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository extends AbstractRepository<Item> {

    public ItemRepository() {
        super(Item.class);
    }

    public List<Item> findById(Long id) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from Item as i where i.person.id = :id")
                .setLong("id", id);

        List<Item> list = (List<Item>) query.list();

        return list;
    }

    public List<Item> findByName(String name) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from Item as i where i.person.name = :name")
                .setString("name", name);

        List<Item> list = (List<Item>) query.list();

        return list;
    }
}
