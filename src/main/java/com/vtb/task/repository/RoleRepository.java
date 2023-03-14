package com.vtb.task.repository;

import com.vtb.task.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для {@link Role}
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
