package com.netty.handler;

import com.netty.utils.HandlerSend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

public class MqttMessageHandler {
    private static Logger logger = LoggerFactory.getLogger(MqttMessageHandler.class);
	@Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public static MessageHandler handler() {
		
		return message -> {

            logger.info(" MqttMessageHandler Read  getPayload" + message.getPayload());
            //获取mqtt协议发送过来的消息
            String payload = (String) message.getPayload();
            /**
             * 业务含义,你的业务进行处理的逻辑
             */
            String[] str = payload.split(",");
            String ip = str[0];
            //下发命令
            new HandlerSend(payload,ip).start();

        };

    }

}
