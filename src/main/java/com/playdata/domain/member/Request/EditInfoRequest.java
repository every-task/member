package com.playdata.domain.member.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EditInfoRequest {
    @NotBlank
    private String nickname;
    @NotBlank
    private String profileImageUrl;

}
