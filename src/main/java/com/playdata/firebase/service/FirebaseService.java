package com.playdata.firebase.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.playdata.config.TokenInfo;
import com.playdata.domain.member.exception.FirebaseException;
import org.springframework.stereotype.Service;

@Service
public class FirebaseService {



    public String publishToken(TokenInfo tokenInfo)  {

        String id = tokenInfo.getId().toString();

        try {
            return FirebaseAuth.getInstance().createCustomToken(id);
        }catch (FirebaseAuthException e){
            throw new FirebaseException("Not authenticated.",e);
        }


    }
}
