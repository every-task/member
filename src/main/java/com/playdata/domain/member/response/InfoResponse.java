package com.playdata.domain.member.response;


import com.playdata.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InfoResponse {

    private String email;
    private String nickname;
    private String profileImageUrl;


    public InfoResponse(Member member){

        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.profileImageUrl = member.getProfileImageUrl();

    }
}
