package com.sh.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

// 일정 수정 시 필요한 컬럼만 담고 있음
@Slf4j
@Getter
@Setter
@ToString
public class TaskRequest {
    private String title;
    private String description;
    @JsonDeserialize(using = LocalDateDeserializer.class) // JSON 데이터를 LocalDate타입으로 상호 변환
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
}