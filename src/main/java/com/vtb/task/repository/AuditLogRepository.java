package com.vtb.task.repository;

import com.vtb.task.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для {@link AuditLog}
 */
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
