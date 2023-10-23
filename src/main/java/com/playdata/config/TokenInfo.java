package com.playdata.config;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfo  {

    private UUID id;
    private String email;
    private String nickname;
    private String profileImageUrl;

}
