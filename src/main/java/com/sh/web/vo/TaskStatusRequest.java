package com.sh.web.vo;

import com.sh.constants.TaskStatus;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TaskStatusRequest {
    private TaskStatus status;
}
