package com.corereach.communication;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @Description: 监听springboot，当springboot启动完成后，执行onApplicationEvent方法里面的操作
 * 目前这个启动类暂时不用，因为在start()方法加上PostConstruct注解
 * @Author ga.zhang
 * @Date 2019/12/19 15:02
 * @Version V1.0
 **/
@Component
public class NettyPostProcessor implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        /*只有在springboot本身的上下文对象初始化完成后才执行，其他的上下文对象初始化完成后不执行，因为只有springboot本身的上下文对象才没有父容器*/
//        if (ObjectUtils.isEmpty(contextRefreshedEvent.getApplicationContext().getParent())) {
//
//            /*启动netty*/
//            /*ChatServer.getInstance().start();*/
//
//        }
    }

}
