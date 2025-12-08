package mx.edu.utez.back.service;

import mx.edu.utez.back.model.Product;
import mx.edu.utez.back.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(Product p) {
        try {
            String id = productRepository.save(p, p.getId());
            p.setId(id);
            return p;
        } catch (Exception e) {
            throw new RuntimeException("Error creating product", e);
        }
    }

    public List<Product> findAll() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error finding products", e);
        }
    }

    public Optional<Product> findById(String id) {
        try {
            Product p = productRepository.findById(id);
            return Optional.ofNullable(p);
        } catch (Exception e) {
            throw new RuntimeException("Error finding product", e);
        }
    }

    public void delete(String id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting product", e);
        }
    }
}
