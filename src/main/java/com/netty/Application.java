package com.netty;

import com.netty.service.NettyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName Application
 * @Author zhiwei.jiang
 * @Date 2018/4/16 15:44
 * @Version 1.0
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
    SpringApplication app = new SpringApplication(Application.class);
        try {
        NettyService nettyService = (NettyService)app.run(args).getBean("nettyService");
        nettyService.serviceStart();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
