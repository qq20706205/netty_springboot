package com.netty.service;

import com.netty.handler.NettyInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName NettyService
 * @Author zhiwei.jiang
 * @Date 2018/4/16 15:49
 * @Version 1.0
 */
@Component
public class NettyService {
    @Autowired
    private NettyInitializer nettyInitializer;
    @Value("${netty.server.port}")
    private String port;

    private Logger logger = LoggerFactory.getLogger(NettyService.class);
    private static ServerBootstrap bootstrap;

    private boolean inited = false;

    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    @SuppressWarnings("resource")
    public boolean init(Object param) {
        try {
            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(nettyInitializer);

        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return false;
        }
        return true;
    }
    /*@PostConstruct*/
    public void serviceStart() throws Exception {
        if (!inited) {
            inited = init(null);
            if (!inited){

                throw new Exception(" netty-service start fail!");
            }
        }

        try {
            logger.info("Server start……");
            bootstrap.bind( Integer.valueOf(port)).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
