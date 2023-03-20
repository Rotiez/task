package com.vtb.task.dto.response;

import com.vtb.task.validation.interfaces.ValidateTaskStatus;
import com.vtb.task.validation.interfaces.ValidateTaskType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ответ для задачи с атрибутами
 * {@link #name}, {@link #type}, {@link #status}, {@link #owner}, {@link #executor},
 */
@Data
@Builder
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class TaskResponse {
    @NotNull(message = "Название задачи не может быть нулевым")
    @Pattern(regexp = "^[A-Z А-Я].*", message = "Название задачи должно начинаться с заглавной буквы")
    private String name;
    @ValidateTaskType(message = "Тип задачи должен быть: Developing или Analytics")
    private String type;
    @ValidateTaskStatus(message = "Статус задачи должен быть: In process, Waiting или Closed")
    private String status;
    @Pattern(regexp = "^[A-Z А-Я].*", message = "Имя владелеца должно начинаться с заглавной буквы")
    private String owner;
    @Pattern(regexp = "^[A-Z А-Я].*", message = "Имя исполнителя должно начинаться с заглавной буквы")
    private String executor;
}
