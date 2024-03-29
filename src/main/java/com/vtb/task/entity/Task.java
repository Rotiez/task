package com.vtb.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность задачи с атрибутами
 * {@link #taskId}, {@link #name}, {@link #type}, {@link #status}, {@link #owner}, {@link #executor}
 */
@Entity
@Table(name = "tasks")
@Data
@Builder
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    private String name;
    private String type;
    private String status;
    private String owner;
    private String executor;
}
