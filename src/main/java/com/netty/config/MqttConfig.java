package com.netty.config;

import com.netty.handler.MqttMessageHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;


@Configuration
public class MqttConfig {
	
	@Value("${mqtt.server.url.port}")
	private String mqttServerUrl;
	
	@Value("${mqtt.server.username}")
	private String mqttServerUsername;
	
	@Value("${mqtt.server.password}")
	private String mqttServerPassword;
	
	@Value("${mqtt.light.control.client.id}")
	private String controlClientId;
	
	@Value("${mqtt.light.up.client.id}")
	private String upClientId;
	
	@Value("${mqtt.light.control.stream.topic}")
	private String lightControlTopic;
	
	@Value("${mqtt.light.up.stream.topic}")
	private String lightUpStreamTopic;
	
	@Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setServerURIs(mqttServerUrl);
        factory.setUserName(mqttServerUsername);
        factory.setPassword(mqttServerPassword);
        return factory;
    }
	
	@Bean
	public IntegrationFlow mqttInFlow() {
		return IntegrationFlows.from(mqttInbound())
				.transform(p ->p)
				.handle(MqttMessageHandler.handler())
				.get();
	}

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

	@Bean
	public MessageProducerSupport mqttInbound() {
		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(controlClientId,mqttClientFactory(), lightControlTopic);
		adapter.setCompletionTimeout(5000);
		adapter.setConverter(new DefaultPahoMessageConverter());
		adapter.setQos(1);
		adapter.setOutputChannel(mqttInputChannel());
		return adapter;
	}
	
	@Bean
	public IntegrationFlow mqttOutFlow() {
		return IntegrationFlows.from(outChannel())
        .handle(mqttOutbound())
        .get();
	}
	
	@Bean
    public MessageChannel outChannel() {
        return new DirectChannel();
    }

	@Bean
	public MessageHandler mqttOutbound() {
		MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(upClientId, mqttClientFactory());
		messageHandler.setAsync(true);
		messageHandler.setDefaultTopic(lightUpStreamTopic);
		return messageHandler;
	}

}
