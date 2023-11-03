package com.playdata.config;


import com.playdata.domain.member.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtService {

    private final String secretKey = "anfoawhfafawkefhbwkjlfeopwehfolawefh";
    // 이 키는 다른곳으로 옮겨야한다.


    public String makeAccessToken(Member member){
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

    public TokenInfo parseAccessToken(String token) {
        Claims body = isValid(token);
        return buildAccessToken(body);
    }
    public UUID parseRefreshToken(String token) {
        Claims body = isValid(token);
        return parseUUID(body);
    }


    public Claims isValid(String token){
        return  (Claims) Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parse(token)
                .getBody();
    }

    public String makeRefreshToken(String uuid){

        Map<String, Object> claims = new HashMap<>();

        claims.put("id",uuid);

        String refreshToken = Jwts
                .builder()
                .setClaims(claims)
                .setExpiration(new Date(
                        System.currentTimeMillis()
                                + + (1000L * 60 * 60 * 24 * 30) // 3일?
                ))
                .signWith(SignatureAlgorithm.HS256,secretKey.getBytes())
                .compact();

        return refreshToken;

    }
    public Cookie makeCookie(String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setDomain("104.154.101.83"); // 이건 나중에 다시 세팅할것
        cookie.setHttpOnly(true);
        // cookie.setSecure(true); // 이건 나중에 https에서 사용가능함
        cookie.setPath("/");
        cookie.setMaxAge(1000 * 60 * 60 * 24);
        return cookie;
    }

    public Cookie setRefreshTokenInCookie(String uuid){
        String refreshToken = makeRefreshToken(uuid);
        return makeCookie(refreshToken);

    }

    private UUID parseUUID(Claims body){
        return UUID.fromString(body.get("id", String.class));
    }

    private TokenInfo buildAccessToken(Claims body){
        return TokenInfo.builder()
                .id(UUID.fromString(body.get("id", String.class)))
                .email(body.get("email", String.class))
                .nickname(body.get("nickname", String.class))
                .profileImageUrl(body.get("profileImageUrl", String.class))
                .build();
    }
}
