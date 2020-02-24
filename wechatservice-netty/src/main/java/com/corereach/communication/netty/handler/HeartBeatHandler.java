package com.corereach.communication.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description: 用于监测channel的心跳handler，继承ChannelInboundHandlerAdapter从而不需要实现channelRead0方法
 * @Author ga.zhang
 * @Date 2019/12/17 9:38
 * @Version V1.0
 **/
@Slf4j
@Component
@ChannelHandler.Sharable
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        /**判断evt是否是空闲状态（用于触发用户事件，包含读空闲/写空闲/读写空闲）*/
        if (evt instanceof IdleStateEvent) {
            /**强制类型转换*/
            IdleStateEvent event = (IdleStateEvent) evt;
//            switch (event.state()) {
//                case READER_IDLE:
//                    log.info("进入读空闲");
//                    break;
//                case WRITER_IDLE:
//                    log.info("进入写空闲");
//                    break;
//                case ALL_IDLE:
//                    log.info("channel关闭前，users数量为：{}",WebSocketChatHandler.users.size());
//                    Channel channel = ctx.channel();
//                    /**关闭无用的channel，以防资源浪费*/
//                    channel.close();
//                    log.info("channel关闭后，users数量为：{}",WebSocketChatHandler.users.size());
//                    break;
//                default:
//                    break;
//            }
            if (event.state() == IdleState.READER_IDLE) {
                log.info("进入读空闲");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.info("进入写空闲");
            } else if (event.state() == IdleState.ALL_IDLE) {
                log.info("channel关闭前，users数量为：{}", WebSocketChatHandler.users.size());
                Channel channel = ctx.channel();
                /**关闭无用的channel，以防资源浪费*/
                channel.close();
                log.info("channel关闭后，users数量为：{}", WebSocketChatHandler.users.size());
            }
        }
    }

}
