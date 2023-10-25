package com.playdata.firebase.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.playdata.config.TokenInfo;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FirebaseService {



    public String publishToken(TokenInfo tokenInfo)  {

        String uid = tokenInfo.getId().toString();

        try {
            return FirebaseAuth.getInstance().createCustomToken(uid);
        }catch (FirebaseAuthException e){
            throw new RuntimeException("다시 시도해주세요.");
        }


    }
}
