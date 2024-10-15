package com.ipa.learnkstream73.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ipa.learnkstream73.exceptionhandler.CustomStreamsUncaughtExceptionHandler;
import com.ipa.learnkstream73.topology.GreetingsStreamsTopology;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.config.StreamsBuilderFactoryBeanConfigurer;
import org.springframework.kafka.config.TopicBuilder;

@AllArgsConstructor
@Configuration
public class LearnKstream73Configuration {
    public final KafkaProperties kafkaProperties;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }

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

    @Bean
    public StreamsBuilderFactoryBeanConfigurer streamsBuilderFactoryBeanConfigurer() {
        return streamsBuilderFactoryBean -> {
            streamsBuilderFactoryBean.setStreamsUncaughtExceptionHandler(new CustomStreamsUncaughtExceptionHandler());
        };
    }

    /* another way to handle and recover from deserialization errors
    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs() {
        var kafkaStreamsProps = kafkaProperties.buildStreamsProperties();

        kafkaStreamsProps.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, RecoveringDeserializationExceptionHandler.class);
        kafkaStreamsProps.put(RecoveringDeserializationExceptionHandler.KSTREAM_DESERIALIZATION_RECOVERER, recoverer());

        return new KafkaStreamsConfiguration(kafkaStreamsProps);
    }

    @Bean
    public ConsumerRecordRecoverer recoverer() {
        return (ConsumerRecord, e) -> {
            log.error("exception is {}, failed record: {}, consumerRecord, e.getMessage(), e);
        };
    }

    @Bean
    public DeadLetterPublishingRecoverer recoverer_by_writing_DLQ() {
        return new DeadLetterPublishingRecoverer(kafkaTemplate(),
                (record, ex) -> new TopicPartition("recovererDLQ", -1));
    }
     */
}
