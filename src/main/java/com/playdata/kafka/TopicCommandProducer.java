package com.playdata.kafka;

import com.playdata.domain.member.kafka.MemberKafka;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class TopicCommandProducer {

    private final KafkaTemplate<String, MemberKafka> template;
    public void sendMember(MemberKafka member){
        template.send("member", member);
    }

}
