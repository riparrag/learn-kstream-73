package com.ipa.learnkstream73.config;

import com.ipa.learnkstream73.topology.GreetingsStreamsTopology;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class LearnKstream73Configuration {
    @Bean
    public NewTopic greetingsTopic() {
        return TopicBuilder.name(GreetingsStreamsTopology.GREETINGS_TOPOLOGY_NAME)
                .partitions(2)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic greetingsOutputTopic() {
        return TopicBuilder.name(GreetingsStreamsTopology.GREETINGS_OUTPUT)
                .partitions(2)
                .replicas(1)
                .build();
    }
}
