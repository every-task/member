package com.playdata.member.service;

import com.playdata.config.JwtService;
import com.playdata.config.TokenInfo;
import com.playdata.domain.member.Request.EditInfoRequest;
import com.playdata.domain.member.Request.EditPassRequest;
import com.playdata.domain.member.Request.LoginRequest;
import com.playdata.domain.member.Request.SignupRequest;
import com.playdata.domain.member.entity.Member;
import com.playdata.domain.member.exception.ExistEmailException;
import com.playdata.domain.member.exception.IncorrectContactException;
import com.playdata.domain.member.exception.LoginFailException;
import com.playdata.domain.member.kafka.MemberKafka;
import com.playdata.domain.member.repository.MemberRepository;
import com.playdata.domain.member.response.InfoResponse;
import com.playdata.domain.member.response.LoginResponse;
import com.playdata.kafka.TopicCommandProducer;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TopicCommandProducer topicCommandProducer;

    @Transactional
    public void signup(SignupRequest signupRequest){
        boolean existent = memberRepository.findByEmail(signupRequest.getEmail()).isPresent();

        if(existent){
            throw new ExistEmailException("This email has already been registered.");
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

    public LoginResponse login(LoginRequest loginRequest, HttpServletResponse response){
        Member member = findByEmail(loginRequest.getEmail());

        isMissMatch(loginRequest.getPassword(), member.getPassword());


        String token = jwtService.makeAccessToken(member);


        addCookies( response, member);


        return new LoginResponse(token, member.getProfileImageUrl());

    }



    public InfoResponse getInfo(TokenInfo tokenInfo){

        return new InfoResponse(findByEmail(tokenInfo.getEmail()));
    }

    @Transactional
    public void editInfo(TokenInfo tokenInfo, EditInfoRequest editInfoRequest){
        Member member = findById(tokenInfo.getId());

        member.edit(editInfoRequest);

        memberRepository.save(member);

        MemberKafka memberKafka = MemberKafka.builder()
                .id(member.getId())
                .profileImageUrl(member.getProfileImageUrl())
                .nickname(member.getNickname())
                .build();

        topicCommandProducer.sendMember(memberKafka);

    }

    @Transactional
    public void editPass(TokenInfo tokenInfo, EditPassRequest editPassRequest){
        Member member = findByEmail(tokenInfo.getEmail());

        isMissMatch(editPassRequest.getPassword(), member.getPassword());

        member.editPass(passwordEncoder.encode(editPassRequest.getNewPassword()));
        memberRepository.save(member);

    }

    public Member findByEmail(String email){
       return memberRepository.findByEmail(email).
               orElseThrow(() -> new LoginFailException("The ID or password is incorrect."));
    }

    public Member findById(UUID id) {
        return memberRepository
                .findById(id)
                .orElseThrow( ()->new IncorrectContactException("wrong approach"));
    }

    private void isMissMatch(String inputPassword, String savedPassword) {
         if(!passwordEncoder.matches(inputPassword, savedPassword)) {
             throw new LoginFailException("The ID or password is incorrect.");
         }
    }

    private void addCookies( HttpServletResponse response, Member member) {
        Cookie refreshCookie = jwtService.setRefreshTokenInCookie(member.getId().toString());

        response.addCookie(refreshCookie);

    }




}
