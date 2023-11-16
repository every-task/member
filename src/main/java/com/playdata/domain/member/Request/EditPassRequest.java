package com.playdata.domain.member.Request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EditPassRequest {
    @NotBlank
    private String password;
    @NotBlank
    private String newPassword;

}
