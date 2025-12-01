package mx.edu.utez.back.service;

import mx.edu.utez.back.model.Product;
import mx.edu.utez.back.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(Product p) {
        return productRepository.save(p);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
