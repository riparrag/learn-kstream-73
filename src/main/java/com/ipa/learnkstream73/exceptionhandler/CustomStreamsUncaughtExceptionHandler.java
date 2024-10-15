package com.ipa.learnkstream73.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;

@Slf4j
public class CustomStreamsUncaughtExceptionHandler implements StreamsUncaughtExceptionHandler {
    /**
     * @param throwable
     * @return
     */
    @Override
    public StreamThreadExceptionResponse handle(Throwable throwable) {
        log.error("Uncaught Exception {}", throwable.getMessage(), throwable);

        return StreamThreadExceptionResponse.SHUTDOWN_APPLICATION;
    }
}
