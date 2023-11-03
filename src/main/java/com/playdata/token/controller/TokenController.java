package com.playdata.token.controller;

import com.playdata.config.TokenInfo;
import com.playdata.domain.member.response.LoginResponse;
import com.playdata.token.Service.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/token")
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/refresh")
    public LoginResponse republish(
            @CookieValue(name = "refreshToken",defaultValue = "none") String refreshToken,
            @AuthenticationPrincipal() TokenInfo tokenInfo,
            HttpServletResponse response) {


        LoginResponse republish = tokenService.republish(refreshToken, tokenInfo,response);

        return republish;
    }

    @PostMapping("/welcome")
    public LoginResponse seeYouAgain(
            @CookieValue(name = "refreshToken",defaultValue = "none") String refreshToken,
            HttpServletResponse response
    ) {


        LoginResponse loginResponse = tokenService.seeYouAgain(refreshToken, response);

        return loginResponse;
    }


}
