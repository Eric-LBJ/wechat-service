package com.corereach.communication;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ga.zhang
 */
@SpringBootApplication
@MapperScan("com.corereach.communication.dal.mapper")
@DubboComponentScan("com.corereach.communication.service")
public class WeChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeChatApplication.class, args);
    }

}
