package mx.edu.utez.back.controller;

import mx.edu.utez.back.model.Product;
import mx.edu.utez.back.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product p) {
        return ResponseEntity.ok(productService.create(p));
    }

    @GetMapping
    public ResponseEntity<List<Product>> list() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable String id) {
        Optional<Product> product = productService.findById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable String id, @RequestBody Product p) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            Product existing = product.get();
            if (p.getName() != null)
                existing.setName(p.getName());
            if (p.getSku() != null)
                existing.setSku(p.getSku());
            if (p.getPrice() != null)
                existing.setPrice(p.getPrice());
            if (p.getStock() != null)
                existing.setStock(p.getStock());
            if (p.getDescription() != null)
                existing.setDescription(p.getDescription());
            if (p.getPhotoBase64() != null)
                existing.setPhotoBase64(p.getPhotoBase64());
            if (p.getQrCode() != null)
                existing.setQrCode(p.getQrCode());
            if (p.getAvailable() != null)
                existing.setAvailable(p.getAvailable());
            return ResponseEntity.ok(productService.create(existing));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
