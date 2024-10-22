package com.ipa.learnkstream73.topology;

import com.ipa.learnkstream73.domain.Greeting;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.TopologyTestDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class GreetingsStreamsTopologyTest {
    TopologyTestDriver topologyTestDriver;
    //ObjectMapper objectMapper = new ObjectMapper();
    GreetingsStreamsTopology greetingsStreamsTopology = new GreetingsStreamsTopology();
    StreamsBuilder streamsBuilder;

    TestInputTopic<String, Greeting> inputTopic;
    TestOutputTopic<String, Greeting> outputTopic;

    @BeforeEach
    void setup() {
        streamsBuilder = new StreamsBuilder();
        greetingsStreamsTopology.process(streamsBuilder);
        topologyTestDriver = new TopologyTestDriver(streamsBuilder.build());

        var keySerdes = Serdes.String();
        var valueSerdes = new JsonSerde<>(Greeting.class);

        inputTopic = topologyTestDriver.createInputTopic(GreetingsStreamsTopology.GREETINGS_TOPOLOGY_NAME, keySerdes.serializer(), valueSerdes.serializer());
        outputTopic = topologyTestDriver.createOutputTopic(GreetingsStreamsTopology.GREETINGS_OUTPUT, keySerdes.deserializer(), valueSerdes.deserializer());
    }

    @AfterEach
    void tearDown() {
        topologyTestDriver.close();
    }

    @Test
    void test_greetings() {
        //given
        inputTopic.pipeInput("A", new Greeting("hello world", LocalDateTime.now()));

        //when

        //test

        //then
        assertEquals(1, outputTopic.getQueueSize());
        var firstOutput = outputTopic.readKeyValue();
        assertEquals("A", firstOutput.key);
        assertEquals("HELLO WORLD", firstOutput.value.message());
        assertNotNull(firstOutput.value.timestamp());
    }
}