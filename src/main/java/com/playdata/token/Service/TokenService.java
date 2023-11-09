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

        checkApproach(refreshToken, tokenInfo, id);

        Member member = memberService.findById(id);

        String token = jwtService.makeAccessToken(member);

        addCookies( response, id);

        return new LoginResponse(token);
    }



    public LoginResponse seeYouAgain(String refreshToken,HttpServletResponse response) {

        UUID id = jwtService.parseRefreshToken(refreshToken);

        Member member = memberService.findById(id);

        String token = jwtService.makeAccessToken(member);

        addCookies(response,id);

        return new LoginResponse(token);
    }

    public String goodBye(HttpServletResponse response){

        Cookie cookie = jwtService.deleteRefreshCookie();
        response.addCookie(cookie);


        return "OK";
    }

    private void checkApproach(String refreshToken, TokenInfo tokenInfo, UUID id) {
        if(refreshToken.equals("none") || !(id.equals(tokenInfo.getId()))) {
            throw new IncorrectContactException("wrong approach");
        }
    }

    private void addCookies(HttpServletResponse response, UUID id) {
        Cookie refreshCookie = jwtService.setRefreshTokenInCookie(id.toString());

        response.addCookie(refreshCookie);
    }



}
