package com.netty.handler;

import com.google.gson.Gson;
import com.netty.utils.ChannelMap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName NettyHandler
 * @Author zhiwei.jiang
 * @Date 2018/4/16 15:53
 * @Version 1.0
 */
@Component
public class NettyHandler extends SimpleChannelInboundHandler<String> {

    @Autowired
    private MessageGateway messageGateway;
    /**
     * 日志记录
     */
    private Logger logger = LoggerFactory.getLogger(NettyHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info(" RamoteAddress : "+ ctx.channel().remoteAddress() + " active !");
        String ipAndHost = ctx.channel().remoteAddress().toString();
        String ip = ipAndHost.substring(1, ipAndHost.lastIndexOf(":"));
        ChannelMap.addChannel(ip,ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info(" DealHandler channel read complete ... ");
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Channel channel = ctx.channel();
        if (channel.isActive()) {
            ctx.close();
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String hexString){

        try {
            logger.info(" DealHandler channelRead0  " + hexString);
            //解析hexString hexString是业务字段,客户端返回的,需要你进行处理的字段.
            String json = new Gson().toJson(hexString);
            logger.info("===>> json: " + json);
            messageGateway.write(json);

        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
        }
    }
}
