package com.bookstore.admin.controller;

import com.bookstore.admin.dto.*;
import com.bookstore.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Admin management APIs")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/register")
    @Operation(summary = "Register a new admin (first time or SUPER_ADMIN only)")
    public ResponseEntity<AdminAuthResponse> register(
            @Valid @RequestBody AdminRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(adminService.register(request, null));
    }

    @PostMapping("/login")
    @Operation(summary = "Admin login")
    public ResponseEntity<AdminAuthResponse> login(
            @RequestBody AdminLoginRequest request) {
        return ResponseEntity.ok(adminService.login(request));
    }

    @GetMapping("/all-users")
    @Operation(summary = "List all users")
    public ResponseEntity<List<Object>> getAllUsers(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(adminService.getAllUsers(userDetails.getUsername()));
    }

    @PutMapping("/products/{id}")
    @Operation(summary = "Update a product")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                adminService.updateProduct(id, request, userDetails.getUsername()));
    }

    @DeleteMapping("/products/{id}")
    @Operation(summary = "Delete a product")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        adminService.deleteProduct(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/orders")
    @Operation(summary = "List all orders")
    public ResponseEntity<List<Object>> getAllOrders(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(adminService.getAllOrders(userDetails.getUsername()));
    }

    @PutMapping("/orders/{id}/status")
    @Operation(summary = "Update order status")
    public ResponseEntity<Object> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody OrderStatusRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                adminService.updateOrderStatus(id, request.getStatus(), userDetails.getUsername()));
    }
}