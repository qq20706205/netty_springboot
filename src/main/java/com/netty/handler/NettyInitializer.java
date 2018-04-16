package com.netty.handler;

import com.netty.utils.ProtocoDecoderUtil;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName NettyInitializer
 * @Author zhiwei.jiang
 * @Date 2018/4/16 15:51
 * @Version 1.0
 */
@Component
public class NettyInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    private NettyHandler nettyHandler;
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("framer", new ProtocoDecoderUtil());
        pipeline.addLast("handler", nettyHandler);
    }
}
