package com.datatransfer.dt2.configs;

import org.springframework.context.ApplicationEvent;

public class FixedDelayUpdatedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	private final long fixedDelay;

    public FixedDelayUpdatedEvent(Object source, long fixedDelay) {
        super(source);
        this.fixedDelay = fixedDelay;
    }

    public long getFixedDelay() {
        return fixedDelay;
    }
}
