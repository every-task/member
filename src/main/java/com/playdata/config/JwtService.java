package com.playdata.config;


import com.playdata.domain.member.entity.Member;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtService {

    private final String secretKey = "anfoawhfafawkefhbwkjlfeopwehfolawefh";
    // 이 키는 다른곳으로 옮겨야한다.


    public String makeToken(Member member){
        Map<String, Object> claims = new HashMap<>();

        claims.put("id",member.getId().toString());
        claims.put("email",member.getEmail());
        claims.put("nickname",member.getNickname());
        claims.put("profileImageUrl",member.getProfileImageUrl());

        String token = Jwts
                .builder()
                .setClaims(claims)
                .setExpiration(new Date(
                        System.currentTimeMillis()
                        + + (1000L * 60 * 60 * 2) // 2시간
                ))
                .signWith(SignatureAlgorithm.HS256,secretKey.getBytes())
                .compact();
        return token;
    }

    public TokenInfo parseToken(String token){
        Claims body = (Claims) Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parse(token)
                .getBody();

        return TokenInfo.builder()
                .id(UUID.fromString(body.get("id", String.class)))
                .email(body.get("email", String.class))
                .nickname(body.get("nickname", String.class))
                .profileImageUrl(body.get("profileImageUrl", String.class))
                .build();
    }
}
