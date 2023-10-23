package com.playdata.domain.member.Request;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EditPassRequest {

    private String password;
    private String newPassword;

}
