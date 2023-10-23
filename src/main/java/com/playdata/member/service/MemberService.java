package com.playdata.member.service;

import com.playdata.config.JwtService;
import com.playdata.config.TokenInfo;
import com.playdata.domain.member.Request.EditInfoRequest;
import com.playdata.domain.member.Request.EditPassRequest;
import com.playdata.domain.member.Request.LoginRequest;
import com.playdata.domain.member.Request.SignupRequest;
import com.playdata.domain.member.entity.Member;
import com.playdata.domain.member.repository.MemberRepository;
import com.playdata.domain.member.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void signup(SignupRequest signupRequest){
        Member byEmail = findByEmail(signupRequest.getEmail());

        if(byEmail != null){
            throw new RuntimeException("이미 가입된 이메일 입니다.");
            // 이후에 추가적으로 고도화 예정.
        }

        Member member = Member.builder()
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .profileImageUrl(signupRequest.getProfileImageUrl())
                .nickname(signupRequest.getNickname())
                .build();

        memberRepository.save(member);
    }

    public LoginResponse login(LoginRequest loginRequest){
        Member member = findByEmail(loginRequest.getEmail());

        if(member == null){
            throw new RuntimeException("아이디 혹은 비밀번호가 틀렸습니다.");
        }
        if(!passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())){
            throw new RuntimeException("아이디 혹은 비밀번호가 틀렸습니다.");
        }

        String token = jwtService.makeToken(member);
        return new LoginResponse(token);


        //TODO 이후 추가적으로 고도화 예정. 비밀번호는 줄 필요 없음.
        // jwt 토큰으로 발행하여 줄 예정.
        // 에러 처리도 필요함. 스프링 시큐리티도 적용예정.
    }

    @Transactional
    public void editInfo(TokenInfo tokenInfo, EditInfoRequest editInfoRequest){
        Member member = memberRepository.findByEmail(tokenInfo.getEmail());

        member.edit(editInfoRequest);

        memberRepository.save(member);

    }

    @Transactional
    public void editPass(TokenInfo tokenInfo, EditPassRequest editPassRequest){
        Member member = memberRepository.findByEmail(tokenInfo.getEmail());

        if(!passwordEncoder.matches(editPassRequest.getPassword(), member.getPassword())){
            throw new RuntimeException("비밀번호가 다릅니다.");
            // 이후 수정 필요
        }

        member.editPass(passwordEncoder.encode(editPassRequest.getNewPassword()));
        memberRepository.save(member);

    }

    public Member findByEmail(String email){
       return memberRepository.findByEmail(email);
    }


}
