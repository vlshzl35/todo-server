package com.sh.persist;

import com.sh.constants.TaskStatus;
import com.sh.persist.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findAllByDueDate(Date dueDate);
    List<TaskEntity> findAllByStatus(TaskStatus status);
}