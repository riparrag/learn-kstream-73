package com.ipa.learnkstream73.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.errors.ProductionExceptionHandler;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.util.Map;

@Slf4j
//Production == Serialization
public class CustomStreamsProductionExceptionHandler implements ProductionExceptionHandler {
    int errorCounter = 0;

    @Override
    public ProductionExceptionHandlerResponse handle(ProducerRecord<byte[], byte[]> producerRecord, Exception e) {
        log.error("Serialization(Production) Exception: {}, with kafka record: {}", e.getMessage(), producerRecord, e);
        errorCounter++;
        log.info("errorCounter: {}", errorCounter);
        return ProductionExceptionHandlerResponse.CONTINUE;
    }

    /**
     * @param map
     */
    @Override
    public void configure(Map<String, ?> map) {}
}
