package com.bookstore.admin.service;

import com.bookstore.admin.entity.AuditLog;
import com.bookstore.admin.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public void log(String adminEmail, String action, String detail) {
        auditLogRepository.save(AuditLog.builder()
                .adminEmail(adminEmail)
                .action(action)
                .detail(detail)
                .build());
    }
}