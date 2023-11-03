package com.playdata.token.Service;

import com.playdata.config.JwtService;
import com.playdata.config.TokenInfo;
import com.playdata.domain.member.entity.Member;
import com.playdata.domain.member.exception.IncorrectContactException;
import com.playdata.domain.member.response.LoginResponse;
import com.playdata.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtService jwtService;
    private final MemberService memberService;


    public LoginResponse republish(String refreshToken,
                                   TokenInfo tokenInfo,
                                   HttpServletResponse response) {

        UUID id = jwtService.parseRefreshToken(refreshToken);

        if(checkApproach(refreshToken, tokenInfo, id)) {
            throw new IncorrectContactException("wrong approach");
        }

        String token = makeTokenAndSetCookie(id, response);

        return new LoginResponse(token);
    }

    public LoginResponse seeYouAgain(String refreshToken,HttpServletResponse response) {

        UUID id = jwtService.parseRefreshToken(refreshToken);

        String token = makeTokenAndSetCookie(id, response);

        return new LoginResponse(token);
    }

    private String makeTokenAndSetCookie(UUID id, HttpServletResponse response) {
        Member member = memberService.findById(id);

        String token = jwtService.makeAccessToken(member);

        Cookie cookie = jwtService.setRefreshTokenInCookie(member.getId().toString());

        response.addCookie(cookie);
        return token;
    }
    private boolean checkApproach(String refreshToken, TokenInfo tokenInfo, UUID uuid) {
        return refreshToken.equals("none") || !(uuid.equals(tokenInfo.getId()));
    }
}
