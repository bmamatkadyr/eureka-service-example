package productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

}

@RestController
@RequestMapping("/products")
class ProductHttpController {

    private final ProductRepo productRepo;

    public ProductHttpController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Product>> getProductsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(productRepo.getProductByUserId(userId));
    }
}

@Repository
class ProductRepo {
    public List<Product> getProductByUserId(Long userId) {
        //  return random 5 products
        return List.of(
                new Product("product1", "description1"),
                new Product("product2", "description2"),
                new Product("product3", "description3"));
    }
}

record Product(String name, String description) {
}
