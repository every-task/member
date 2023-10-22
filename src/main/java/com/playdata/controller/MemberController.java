package com.playdata.controller;

import com.playdata.domain.member.Request.SignupRequest;
import com.playdata.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/member")
public class MemberController {

    private final MemberService memberService;
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody SignupRequest signupRequest){
        memberService.signup(signupRequest);
    }
}
