package com.vtb.task.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Сущность роли с атрибутами
 * {@link #name}, {@link #users}
 * наследуется от {@link BaseEntity}
 */
@Entity
@Table(name = "roles")
@Data
public class Role extends BaseEntity{
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;
}
