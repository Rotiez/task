package com.vtb.task.repository;

import com.vtb.task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для {@link User}
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
}
