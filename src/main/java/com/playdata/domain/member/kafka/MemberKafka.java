package com.playdata.domain.member.kafka;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class MemberKafka {
    private UUID id;
    private String nickname;
    private String profileImageUrl;
}
