package com.playdata.domain.member.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class LoginRequest {
    @NotBlank(message = "Blank is not allowed")
    private String email;
    @NotBlank(message = "Blank is not allowed")
    private String password;
}
