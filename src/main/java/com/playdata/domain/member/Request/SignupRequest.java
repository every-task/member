package com.playdata.domain.member.Request;

import com.playdata.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequest {

    private String email;
    private String password;
    private String nickname;
    private String profileImageUrl;


    public Member toEntity(){
        return new Member(null,email,password,nickname,profileImageUrl);
    }
}
