package model;

import com.sh.constants.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

//    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;

    private String dueDate;

//    @CreationTimestamp
//    @Column(insertable = false, updatable = false)
    private LocalDateTime createdAt;

//    @UpdateTimestamp
//    @Column(insertable = false, updatable = false)
    private LocalDateTime updatedAt;
}
