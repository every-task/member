package com.playdata.domain.member.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EditInfoRequest {
    private String nickname;
    private String profileImageUrl;

}
