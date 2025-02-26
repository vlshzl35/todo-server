package com.sh.service;

import com.sh.constants.TaskStatus;
import com.sh.persist.TaskRepository;
import com.sh.persist.entity.TaskEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Task;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public Task add(String title, String description, LocalDate dueDate){
        var e = TaskEntity.builder()
                .title(title)
                .description(description)
                .dueDate(Date.valueOf(dueDate))
                .status(TaskStatus.TODO)
                .build();

        var saved = this.taskRepository.save(e);
        return entityToObject(saved);
    }

    // task의 모든 목록 조회
    public List<Task> getAll() {
        return this.taskRepository.findAll().stream()
                .map(this::entityToObject) // entity객체를 task객체로 변환
                .collect(Collectors.toList());
    }

    // 마감일에 해당하는 task 목록 조회
    public List<Task> getByDueDate(String dueDate) {
        return this.taskRepository.findAllByDueDate(Date.valueOf(dueDate)).stream()
                .map(this::entityToObject)
                .collect(Collectors.toList());
    }

    // status에 해당하는 task 목록 조회
    public List<Task> getByStatus(TaskStatus status) {
        return this.taskRepository.findAllByStatus(status).stream()
                .map(this::entityToObject)
                .collect(Collectors.toList());
    }

    // 특정 id에 해당하는 task 목록 조회
    public Task getOne(Long id) {
        var entity = this.getById(id);
        return this.entityToObject(entity);
    }

    private TaskEntity getById(Long id) {
        return this.taskRepository.findById(id)
                .orElseThrow(() -> // id에 해당하는 값이 없을 경우 에러메시지 출력
                        new IllegalArgumentException(String.format("not exists task id [%d]", id)));
    }

    public Task update(Long id, String title, String description, LocalDate dueDate) {
        var exists = this.getById(id);

        exists.setTitle(Strings.isEmpty(title) ?
                exists.getTitle() : title);
        exists.setDescription(Strings.isEmpty(description) ?
                exists.getDescription() : description);

        exists.setDueDate(Objects.isNull(dueDate) ?
                exists.getDueDate() : Date.valueOf(dueDate));

        var updated = this.taskRepository.save(exists);
        return this.entityToObject(updated);
    }

    public Task updateStatus(Long id, TaskStatus status) {
        var entity = this.getById(id);

        entity.setStatus(status);

        var saved = this.taskRepository.save(entity);

        return this.entityToObject(saved);
    }

    public boolean delete(Long id) {
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            log.error("an error occurred while deleting [{}]", e.toString());
            return false;
        }
        return true;
    }

    private Task entityToObject(TaskEntity e){
        return Task.builder()
                .id(e.getId())
                .title(e.getTitle())
                .description(e.getDescription())
                .status(e.getStatus())
                .dueDate(e.getDueDate().toString())
                .createdAt(e.getCreatedAt().toLocalDateTime())
                .updatedAt(e.getUpdatedAt().toLocalDateTime())
                .build();
    }
}
