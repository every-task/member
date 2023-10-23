package com.playdata.service;

import com.playdata.domain.member.Request.SignupRequest;
import com.playdata.domain.member.entity.Member;
import com.playdata.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    public void signup(SignupRequest signupRequest){
        Member byEmail = findByEmail(signupRequest.getEmail());

        if(byEmail != null){
            throw new RuntimeException("이미 가입된 이메일 입니다.");
            // 이후에 추가적으로 고도화 예정.
        }

        memberRepository.save(signupRequest.toEntity());
    }

    public Member findByEmail(String email){
       return memberRepository.findByEmail(email);
    }


}
