package com.vtb.task.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Pattern(regexp = "^[A-Z А-Я].*", message = "Имя должно начинаться с заглавной буквы")
    private String firstname;
    @Pattern(regexp = "^[A-Z А-Я].*", message = "Фамилия должна начинаться с заглавной буквы")
    private String lastname;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @Email
    @NotNull
    private String email;
}
