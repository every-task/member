package com.playdata.controller;

import com.playdata.domain.member.Request.SignupRequest;
import com.playdata.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/member")
public class MemberController {

    private final MemberService memberService;
    @PostMapping("/signup")
    public void signup(@RequestBody SignupRequest signupRequest){
        memberService.signup(signupRequest);
    }
}
