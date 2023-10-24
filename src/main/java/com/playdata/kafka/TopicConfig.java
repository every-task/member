package com.playdata.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {
    public static final String memberCommand = "member";
    @Bean
    public NewTopic memberCommand(){
        return new NewTopic(memberCommand, 1, (short) 1);
    }
}