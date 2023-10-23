package com.playdata.domain.member.entity;

import com.playdata.config.BaseEntity;
import com.playdata.domain.member.Request.EditInfoRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String email;
    private String password;
    private String nickname;
    private String profileImageUrl;

    @Builder
    public Member(String email, String password, String nickname, String profileImageUrl) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public void edit(EditInfoRequest editInfoRequest){
        this.nickname = editInfoRequest.getNickname();
        this.profileImageUrl = editInfoRequest.getProfileImageUrl();

    }

    public void editPass(String password){
        this.password = password;
    }
}
