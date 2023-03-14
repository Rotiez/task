package com.vtb.task.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.List;

/**
 *  Сущность пользователя с атрибутами
 *  {@link #username}, {@link #firstName}, {@link #lastName}, {@link #email}, {@link #password}, {@link #roles},
 *  наследуется от {@link BaseEntity}
 */
@Entity
@Table (name = "users")
@Data
public class User extends BaseEntity{
    @Column(name = "username")
    private String username;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    @Email
    private String email;
    @Column(name = "password")
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;
}
