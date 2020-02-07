package com.corereach.communication.netty;

import com.corereach.communication.netty.initializer.WebSocketServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2019/12/19 14:27
 * @Version V1.0
 **/
@Slf4j
@Component
public class ChatServer {

    @Value("${spring.netty.port}")
    private Integer serverPort;

    @Resource
    private WebSocketServerInitializer webSocketServerInitializer;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ChannelFuture future;

    @PostConstruct
    public void start() throws InterruptedException {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap();
        server.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(webSocketServerInitializer);
        /*这里不需要加同步，因为这里并不是处于main方法中*/
        this.future = server.bind(serverPort).sync();
        log.info("ChatServer启动完毕。。。");
    }

    @PreDestroy
    public void clearUp() throws InterruptedException {
        this.future.channel().closeFuture().sync();
        Future<?> future = workerGroup.shutdownGracefully().sync();
        if (!future.isSuccess()) {
            log.error("workerGroup 无法正常停止:{}", future.cause().getMessage());
        }

        future = bossGroup.shutdownGracefully().sync();
        if (!future.isSuccess()) {
            log.error("bossGroup 无法正常停止:{}", future.cause().getMessage());
        }
        log.info("ChatServer has stopped");
    }

}
