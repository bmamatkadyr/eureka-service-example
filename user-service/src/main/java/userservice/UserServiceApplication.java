package userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}

@RestController
@RequestMapping("/users")
class UserHttpController {

    private final List<User> users = List.of(
            new User(1L, "Syimyk"),
            new User(2L, "Aibek"),
            new User(3L, "Beksultan")
    );

    private final ProductClient productClient;

    UserHttpController(ProductClient productClient) {
        this.productClient = productClient;
    }

    // get users
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(users);
    }

    // get products by user
    @GetMapping("/{userId}/products")
    public ResponseEntity<List<Product>> getProductsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(productClient.getProductsByUserId(userId));
    }
}

@FeignClient("product-service")
interface ProductClient {
    @GetMapping("/products/{userId}")
    List<Product> getProductsByUserId(@PathVariable Long userId);
}

record Product(String name, String description) {
}


record User(Long id, String name) {
}