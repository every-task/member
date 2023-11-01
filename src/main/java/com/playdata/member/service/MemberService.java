package com.playdata.member.service;

import com.playdata.config.JwtService;
import com.playdata.config.TokenInfo;
import com.playdata.domain.member.Request.EditInfoRequest;
import com.playdata.domain.member.Request.EditPassRequest;
import com.playdata.domain.member.Request.LoginRequest;
import com.playdata.domain.member.Request.SignupRequest;
import com.playdata.domain.member.entity.Member;
import com.playdata.domain.member.exception.ExistEmailException;
import com.playdata.domain.member.exception.LoginFailException;
import com.playdata.domain.member.kafka.MemberKafka;
import com.playdata.domain.member.repository.MemberRepository;
import com.playdata.domain.member.response.InfoResponse;
import com.playdata.domain.member.response.LoginResponse;
import com.playdata.kafka.TopicCommandProducer;
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
    private final TopicCommandProducer topicCommandProducer;



    @Transactional
    public void signup(SignupRequest signupRequest){
        boolean nonExistent = memberRepository.findByEmail(signupRequest.getEmail()).isEmpty();

        if(nonExistent){
            throw new ExistEmailException("이미 가입된 이메일 입니다.");
        }

        Member member = Member.builder()
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .profileImageUrl(signupRequest.getProfileImageUrl())
                .nickname(signupRequest.getNickname())
                .build();

        memberRepository.save(member);

        MemberKafka memberKafka = MemberKafka.builder()
                .id(member.getId())
                .profileImageUrl(member.getProfileImageUrl())
                .nickname(member.getNickname())
                .build();

        topicCommandProducer.sendMember(memberKafka);

    }

    public LoginResponse login(LoginRequest loginRequest){
        Member member = findByEmail(loginRequest.getEmail());

        if(isMissMatch(loginRequest, member)){
            throw new LoginFailException("아이디 혹은 비밀번호가 틀렸습니다.");
        }

        String token = jwtService.makeToken(member);
        return new LoginResponse(token);

    }

    public InfoResponse getInfo(TokenInfo tokenInfo){

        return new InfoResponse(findByEmail(tokenInfo.getEmail()));
    }

    @Transactional
    public void editInfo(TokenInfo tokenInfo, EditInfoRequest editInfoRequest){
        Member member = findByEmail(tokenInfo.getEmail());

        member.edit(editInfoRequest);

        memberRepository.save(member);

    }

    @Transactional
    public void editPass(TokenInfo tokenInfo, EditPassRequest editPassRequest){
        Member member = findByEmail(tokenInfo.getEmail());

        if(!passwordEncoder.matches(editPassRequest.getPassword(), member.getPassword())){
            throw new LoginFailException("이메일 혹은 비밀번호가 틀렸습니다.");
            // 이후 수정 필요
        }

        member.editPass(passwordEncoder.encode(editPassRequest.getNewPassword()));
        memberRepository.save(member);

    }

    public Member findByEmail(String email){
       return memberRepository.findByEmail(email).
               orElseThrow(() -> new LoginFailException("이메일 혹은 비밀번호가 틀렸습니다."));
    }


    private boolean isMissMatch(LoginRequest loginRequest, Member member) {
        return !passwordEncoder.matches(loginRequest.getPassword(), member.getPassword());
    }


}
