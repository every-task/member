package com.playdata.member.controller;

import com.playdata.config.TokenInfo;
import com.playdata.domain.member.Request.EditInfoRequest;
import com.playdata.domain.member.Request.EditPassRequest;
import com.playdata.domain.member.Request.LoginRequest;
import com.playdata.domain.member.Request.SignupRequest;
import com.playdata.domain.member.response.InfoResponse;
import com.playdata.domain.member.response.LoginResponse;
import com.playdata.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth/member")
public class MemberController {

    private final MemberService memberService;
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody @Valid SignupRequest signupRequest) {

        memberService.signup(signupRequest);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(
            @RequestBody @Valid LoginRequest loginRequest,
            HttpServletResponse response ) {

        return memberService.login(loginRequest,response);
    }

    @GetMapping("me/info")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public InfoResponse getInfo(@AuthenticationPrincipal TokenInfo tokenInfo) {
        return memberService.getInfo(tokenInfo);
    }

    @PutMapping("/me/info")
    @ResponseStatus(HttpStatus.OK)
    public void editInfo(@AuthenticationPrincipal TokenInfo tokenInfo,
                         @RequestBody @Valid EditInfoRequest editInfoRequest) {

         memberService.editInfo(tokenInfo, editInfoRequest);
    }
    @PutMapping("/me/password")
    @ResponseStatus(HttpStatus.OK)
    public void editPass(@AuthenticationPrincipal TokenInfo tokenInfo,
                         @RequestBody @Valid EditPassRequest editPassRequest) {
        memberService.editPass(tokenInfo,editPassRequest);
    }
}
