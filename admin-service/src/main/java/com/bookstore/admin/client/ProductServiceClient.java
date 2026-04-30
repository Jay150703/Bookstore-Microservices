package com.bookstore.admin.client;

import com.bookstore.admin.config.FeignConfig;
import com.bookstore.admin.dto.ProductRequest;
import com.bookstore.admin.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

// "product-service" must match spring.application.name in product-service
@FeignClient(name = "product-service", path = "/api/products", configuration = FeignConfig.class)
public interface ProductServiceClient {

    @PutMapping("/{id}")
    ProductResponse updateProduct(@PathVariable("id") Long id,
                                  @RequestBody ProductRequest request);

    @DeleteMapping("/{id}")
    void deleteProduct(@PathVariable("id") Long id);
}