package com.playdata.kafka;

import com.playdata.domain.member.exception.KafkaCommandException;
import com.playdata.domain.member.kafka.MemberKafka;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;


@RequiredArgsConstructor
@Service
public class TopicCommandProducer {

    private final KafkaTemplate<String, MemberKafka> template;
    public void sendMember(MemberKafka member){
        CompletableFuture<SendResult<String, MemberKafka>> future = template.send("member", member);


        if(future.isCompletedExceptionally()){
            throw new KafkaCommandException("Publication failed ={%s}".formatted(member.getId().toString()));
        }



    }

}
