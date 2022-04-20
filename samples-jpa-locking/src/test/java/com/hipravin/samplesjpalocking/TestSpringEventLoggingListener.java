package com.hipravin.samplesjpalocking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TestSpringEventLoggingListener {
    static final Logger log = LoggerFactory.getLogger(TestSpringEventLoggingListener.class);

    @EventListener
    public void logEvent(ApplicationEvent event) {
        log.info("{}",event.getClass().getName());
    }
}
