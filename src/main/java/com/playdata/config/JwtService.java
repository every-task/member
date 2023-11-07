package com.playdata.config;


import com.playdata.domain.member.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtService {

    @Value("${config.jwt.secret}")
    private String secretKey;
    // 이 키는 다른곳으로 옮겨야한다.

    @Value("${config.cookie.domain}")
    private String domain;

    public static int TWO_WEEK = 86400 * 30* 6;

    private static long THIRTY_MINUTE = 1000L * 3600;
    private static long THREE_WEEK = 1000L * 3600 * 24 * 14;


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
                        + (THIRTY_MINUTE)
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
                                + (THREE_WEEK)
                ))
                .signWith(SignatureAlgorithm.HS256,secretKey.getBytes())
                .compact();

        return refreshToken;

    }
    public Cookie makeRefreshCookie(String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);

        Cookie refreshCookie = defaultCookieSetting(cookie);

        refreshCookie.setHttpOnly(true);
        refreshCookie.setMaxAge(TWO_WEEK);
        return cookie;
    }


    public Cookie setRefreshTokenInCookie(String uuid){
        String refreshToken = makeRefreshToken(uuid);
        return makeRefreshCookie(refreshToken);

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

    private Cookie defaultCookieSetting(Cookie cookie) {
        cookie.setDomain(domain);
        cookie.setPath("/");
        return cookie;
    }
}
