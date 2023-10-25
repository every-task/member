package com.playdata.firebase.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FirebaseService {



    public String publishToken()  {

        String uid = UUID.randomUUID().toString();

        try {
            return FirebaseAuth.getInstance().createCustomToken(uid);
        }catch (FirebaseAuthException e){
            throw new RuntimeException("다시 시도해주세요.");
        }


    }
}
