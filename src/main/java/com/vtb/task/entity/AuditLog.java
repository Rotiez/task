package com.vtb.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

/**
 * Сущность адуита с атрибутами
 * {@link #id}, {@link #methodName}, {@link #className}, {@link #httpMethod}, {@link #status}, {@link #errorMessage}, {@link #timestamp}
 *
 */
@Entity
@Table(name = "audit_log")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "method_name")
    private String methodName;

    @Column(name = "class_name")
    private String className;

    @Column(name = "http_method")
    private String httpMethod;

    @Column(name = "status")
    private String status;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "timestamp")
    private Date timestamp;
}
