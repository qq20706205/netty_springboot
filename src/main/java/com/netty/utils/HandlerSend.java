package com.netty.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @ClassName HandlerSend
 * @Author zhiwei.jiang
 * @Date 2018/4/16 16:08
 * @Version 1.0
 */
public class HandlerSend {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(HandlerSend.class);
    ByteBuf buff;
    private String ip;
    public HandlerSend(String payload,String ip) {
        this.ip = ip;
        byte[] bytes = payload.getBytes();
        buff = Unpooled.buffer(bytes.length);
        buff.writeBytes(bytes);
    }
    public void start() {
        logger.info(new Date() + "HandlerSend start... ...");
        Channel channel = ChannelMap.getChannelByName(ip);
        if (channel != null && channel.isActive()){
            channel.writeAndFlush(buff);
            logger.info(new Date() + "HandlerSend writeAndFlush... ...");
            //buff.retain();
        }
        logger.info(new Date() + "HandlerSend end... ...");
    }
}
