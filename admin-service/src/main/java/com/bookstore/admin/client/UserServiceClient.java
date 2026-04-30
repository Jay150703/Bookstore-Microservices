package com.bookstore.admin.client;

import com.bookstore.admin.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "product-service", path = "/api/products", configuration = FeignConfig.class)
public interface UserServiceClient {

    // Admin calls this to list all users — add this endpoint to user-service (see note below)
    @GetMapping("/all")
    List<Object> getAllUsers();
}