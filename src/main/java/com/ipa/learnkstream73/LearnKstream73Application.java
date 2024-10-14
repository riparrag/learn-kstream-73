package com.ipa.learnkstream73;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@EnableKafkaStreams
public class LearnKstream73Application {

	public static void main(String[] args) {
		SpringApplication.run(LearnKstream73Application.class, args);
	}

}
