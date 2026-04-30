package com.bookstore.admin.client;

import com.bookstore.admin.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@FeignClient(name = "product-service", path = "/api/products", configuration = FeignConfig.class)
public interface OrderServiceClient {

    @GetMapping("/all")
    List<Object> getAllOrders();

    @PutMapping("/{id}/status")
    Object updateOrderStatus(@PathVariable("id") Long id,
                             @RequestParam("status") String status);
}