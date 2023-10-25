package com.playdata.firebase.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.playdata.config.TokenInfo;
import com.playdata.firebase.service.FirebaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/firebase")
public class FirebaseController {

    private final FirebaseService firebaseService;

    @GetMapping
    public String publishToken(@AuthenticationPrincipal TokenInfo tokenInfo) {
        return firebaseService.publishToken(tokenInfo);
    }
}
