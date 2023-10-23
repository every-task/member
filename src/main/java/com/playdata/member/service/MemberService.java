package com.playdata.member.service;

import com.playdata.domain.member.Request.LoginRequest;
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

    public Member login(LoginRequest loginRequest){
        Member member = findByEmail(loginRequest.getEmail());

        if(member != null){
            throw new RuntimeException("아이디 혹은 비밀번호가 틀렸습니다.");
        }
        if(member.getPassword() != loginRequest.getPassword()){
            throw new RuntimeException("아이디 혹은 비밀번호가 틀렸습니다.");
        }

        return member;
        //TODO 이후 추가적으로 고도화 예정. 비밀번호는 줄 필요 없음.
        // jwt 토큰으로 발행하여 줄 예정.
        // 에러 처리도 필요함. 스프링 시큐리티도 적용예정.
    }

    public Member findByEmail(String email){
       return memberRepository.findByEmail(email);
    }


}
