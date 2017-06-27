package com.app.db;

import com.app.model.User;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends AbstractRepository<User> {

    public UserRepository() {
        super(User.class);
    }

    public User findByUsername(String username) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from User where username = :username")
                .setString("username", username);

        List<User> list = (List<User>) query.list();

        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }

        return null;
    }
}
