package com.ipa.learnkstream73.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ipa.learnkstream73.domain.Greeting;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class GreetingMockDataProducer {
    static String GREETINGS = "greetings";

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        englishGreetings(objectMapper);
        errorGreetings(objectMapper);
        spanishGreetings(objectMapper);

    }

    private static void spanishGreetings(ObjectMapper objectMapper) {
        var spanishGreetings = List.of(
                new Greeting("Hello, Good Morning!", LocalDateTime.now()),
                new Greeting("Hello, Good Evening!", LocalDateTime.now()),
                new Greeting("Hello, Good Night!", LocalDateTime.now())
        );
        spanishGreetings
                .forEach(greeting -> {
                    try {
                        var greetingJSON = objectMapper.writeValueAsString(greeting);
                        var recordMetaData = ProducerUtil.publishMessageSync(GREETINGS, null, greetingJSON);
                        log.info("Published the alphabet message : {} ", recordMetaData);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private static void englishGreetings(ObjectMapper objectMapper) {
        var spanishGreetings = List.of(
                new Greeting("¡Hola buenos dias!", LocalDateTime.now()),
                new Greeting("¡Hola buenas tardes!", LocalDateTime.now()),
                new Greeting("¡Hola, buenas noches!", LocalDateTime.now())
        );
        spanishGreetings
                .forEach(greeting -> {
                    try {
                        var greetingJSON = objectMapper.writeValueAsString(greeting);
                        var recordMetaData = ProducerUtil.publishMessageSync(GREETINGS, null, greetingJSON);
                        log.info("Published the alphabet message : {} ", recordMetaData);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private static void errorGreetings(ObjectMapper objectMapper) {
        var spanishGreetings = List.of(
                new Greeting("error", LocalDateTime.now()),
                new Greeting("no error at all", LocalDateTime.now())
        );
        spanishGreetings
                .forEach(greeting -> {
                    try {
                        var greetingJSON = objectMapper.writeValueAsString(greeting);
                        var recordMetaData = ProducerUtil.publishMessageSync(GREETINGS, null, greetingJSON);
                        log.info("Published the errors message : {} ", recordMetaData);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}