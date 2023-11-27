package com.playdata.domain.member.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class EditInfoRequest {
    @NotBlank(message = "Blank is not allowed")
    private String nickname;
    @NotBlank(message = "Blank is not allowed")
    private String profileImageUrl;

}
