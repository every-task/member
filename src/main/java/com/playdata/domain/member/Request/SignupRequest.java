package com.playdata.domain.member.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class SignupRequest {

    @NotBlank
    @Pattern(regexp = "[a-zA-z0-9]+@[a-zA-z]+[.]+[a-zA-z.]+")
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;
    private String profileImageUrl;


}
