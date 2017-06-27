package com.app.db;

import com.app.model.Item;
import com.app.model.Person;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class PersonRepository extends AbstractRepository<Person> {

    public PersonRepository() {
        super(Person.class);
    }

    public boolean isNameTaken(String name) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from Person where name = :name")
                .setString("name", name);

        List<Person> list = (List<Person>) query.list();

        return (list.isEmpty() || list == null) ? false : true;
    }

    public Person findByName(String name) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from Person where name = :name")
                .setString("name", name);

        List<Person> list = (List<Person>) query.list();

        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }

        return null;
    }

    public void createItem(Person person, Item item) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        item.setPerson(person);
        session.save(item);

        person.getItems().add(item);
        session.update(person);
        session.flush();

        session.getTransaction().commit();
        session.close();
    }
}
