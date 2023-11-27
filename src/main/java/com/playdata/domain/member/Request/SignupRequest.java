package com.playdata.domain.member.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class SignupRequest {
    @NotBlank(message = "Blank is not allowed")
    @Email(message = "Invalid email format.")
    private String email;
    @NotBlank(message = "Blank is not allowed")
    private String password;
    @NotBlank(message = "Blank is not allowed")
    private String nickname;
    @NotBlank(message = "Blank is not allowed")
    private String profileImageUrl;


}
