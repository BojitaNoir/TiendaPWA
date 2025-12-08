package mx.edu.utez.back.repository;

import mx.edu.utez.back.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository extends AbstractFirestoreRepository<Product> {

    public ProductRepository() {
        super("products", Product.class);
    }
}
