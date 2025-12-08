package mx.edu.utez.back.repository;

import mx.edu.utez.back.model.Store;
import org.springframework.stereotype.Repository;

@Repository
public class StoreRepository extends AbstractFirestoreRepository<Store> {
    public StoreRepository() {
        super("stores", Store.class);
    }
}
