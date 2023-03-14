package com.vtb.task.repository;

import com.vtb.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для {@link Task}
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByName(String name);
}
