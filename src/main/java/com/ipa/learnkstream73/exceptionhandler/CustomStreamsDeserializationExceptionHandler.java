package com.ipa.learnkstream73.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.errors.DeserializationExceptionHandler;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.util.Map;
@Slf4j
public class CustomStreamsDeserializationExceptionHandler implements DeserializationExceptionHandler {
    int errorCounter = 0;
    /**
     * @param processorContext
     * @param consumerRecord
     * @param e
     * @return
     */
    @Override
    public DeserializationHandlerResponse handle(ProcessorContext processorContext, ConsumerRecord<byte[], byte[]> consumerRecord, Exception e) {
        log.error("Exception: {}, with kafka record: {}", e.getMessage(), consumerRecord, e);
        errorCounter++;
        log.info("errorCounter: {}", errorCounter);
        return DeserializationHandlerResponse.CONTINUE;
    }

    /**
     * @param map
     */
    @Override
    public void configure(Map<String, ?> map) {

    }
}
