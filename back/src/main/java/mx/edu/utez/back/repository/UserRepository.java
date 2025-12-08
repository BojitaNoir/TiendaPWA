package mx.edu.utez.back.repository;

import mx.edu.utez.back.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends AbstractFirestoreRepository<User> {
    public UserRepository() {
        super("users", User.class);
    }
}
