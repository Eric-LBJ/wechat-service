package com.corereach.communication.netty.initializer;

import com.corereach.communication.common.comm.Constants;
import com.corereach.communication.netty.handler.HeartBeatHandler;
import com.corereach.communication.netty.handler.WebSocketChatHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2019/12/17 9:11
 * @Version V1.0
 **/
@Slf4j
@Component
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    @Resource
    private WebSocketChatHandler webSocketChatHandler;

    @Resource
    private HeartBeatHandler heartBeatHandler;

    @Value("${spring.webSocket.path}")
    private String webSocketPath;

    @Value("${spring.httpObjectAggregator.maxContentLength}")
    private Integer maxContentLength;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();

        /**
         * 针对客户端，如果一分钟没有向服务器发送读写心跳（ALL），则主动断开连接
         * 如果是读空闲或者写空闲，则不做处理
         */
        pipeline.addLast(new IdleStateHandler(Constants.READER_IDLE_TIME_SECONDS, Constants.WRITER_IDLE_TIME_SECONDS, Constants.ALL_IDLE_TIME_SECONDS));
        /**自定义的空闲状态监测*/
        pipeline.addLast(heartBeatHandler);

        /*===========================以上处理器用于心跳监测===============================*/

        /*webSocket是基于http协议的，所以需要有http的编解码器*/
        pipeline.addLast(new HttpServerCodec());
        /*netty内置的对写大数据流的处理器*/
        pipeline.addLast(new ChunkedWriteHandler());
        /*
         * 对httpMessage进行聚合处理，聚合成FullHttpRequest或FullHttpResponse
         * 几乎在netty的编程中，使用http请求的都会是使用到此处理器
         */
        pipeline.addLast(new HttpObjectAggregator(maxContentLength));

        /*===========================以上处理器用于支持http协议===============================*/

        /*
         * webSocket服务器处理的协议，用于指定给客户端连接访问的路由：/ws
         * 这个handler会处理一些繁重复杂的工作，会处理握手动作handshaking(close,ping,pong) , ping + pong = 心跳
         * 对于webSocket来讲，都是以frames(帧)进行传输的，不同的数据类型对应不同的frames也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler(webSocketPath));
        /*自定义处理器*/
        pipeline.addLast(webSocketChatHandler);

        /*===========================以上处理器用于支持webSocket协议===============================*/
    }

}
