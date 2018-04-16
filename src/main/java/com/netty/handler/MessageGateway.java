package com.netty.handler;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "outChannel")
public interface MessageGateway {
	void write(String note);
}
