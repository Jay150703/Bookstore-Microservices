package com.bookstore.admin.service;

import com.bookstore.admin.client.*;
import com.bookstore.admin.dto.*;
import com.bookstore.admin.entity.*;
import com.bookstore.admin.exception.ResourceNotFoundException;
import com.bookstore.admin.repository.AdminRepository;
import com.bookstore.admin.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuditService auditService;
    private final ProductServiceClient productServiceClient;
    private final OrderServiceClient orderServiceClient;
    private final UserServiceClient userServiceClient;

    // ── Auth ──────────────────────────────────────────────────────────────────

    public AdminAuthResponse register(AdminRegisterRequest request, String callerEmail) {
        // Only SUPER_ADMIN can register other admins
        if (callerEmail != null) {
            Admin caller = adminRepository.findByEmail(callerEmail)
                    .orElseThrow(() -> new ResourceNotFoundException("Caller not found"));
            if (caller.getRole() != AdminRole.SUPER_ADMIN) {
                throw new RuntimeException("Only SUPER_ADMIN can register new admins");
            }
        }
        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        AdminRole role = AdminRole.ADMIN;
        if ("SUPER_ADMIN".equalsIgnoreCase(request.getRole())) {
            role = AdminRole.SUPER_ADMIN;
        }
        Admin admin = Admin.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
        adminRepository.save(admin);
        auditService.log(request.getEmail(), "REGISTER", "New admin registered: " + request.getEmail());
        String token = jwtUtil.generateToken(admin.getEmail(), admin.getRole().name());
        return new AdminAuthResponse(token, admin.getEmail(), admin.getRole().name());
    }

    public AdminAuthResponse login(AdminLoginRequest request) {
        Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));
        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(admin.getEmail(), admin.getRole().name());
        return new AdminAuthResponse(token, admin.getEmail(), admin.getRole().name());
    }

    // ── Users (via Feign → User Service) ─────────────────────────────────────

    public List<Object> getAllUsers(String adminEmail) {
        auditService.log(adminEmail, "VIEW_USERS", "Admin viewed all users");
        return userServiceClient.getAllUsers();
    }

    // ── Products (via Feign → Product Service) ────────────────────────────────

    public ProductResponse updateProduct(Long id, ProductRequest request, String adminEmail) {
        ProductResponse updated = productServiceClient.updateProduct(id, request);
        auditService.log(adminEmail, "UPDATE_PRODUCT", "Updated product id: " + id);
        return updated;
    }

    public void deleteProduct(Long id, String adminEmail) {
        productServiceClient.deleteProduct(id);
        auditService.log(adminEmail, "DELETE_PRODUCT", "Deleted product id: " + id);
    }

    // ── Orders (via Feign → Order Service) ───────────────────────────────────

    public List<Object> getAllOrders(String adminEmail) {
        auditService.log(adminEmail, "VIEW_ORDERS", "Admin viewed all orders");
        return orderServiceClient.getAllOrders();
    }

    public Object updateOrderStatus(Long id, String status, String adminEmail) {
        Object updated = orderServiceClient.updateOrderStatus(id, status);
        auditService.log(adminEmail, "UPDATE_ORDER_STATUS", "Order " + id + " → " + status);
        return updated;
    }
}