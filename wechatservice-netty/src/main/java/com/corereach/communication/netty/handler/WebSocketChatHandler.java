package com.corereach.communication.netty.handler;

import com.corereach.communication.common.comm.Constants;
import com.corereach.communication.common.domain.ChatMsgInfoDTO;
import com.corereach.communication.common.utils.JsonUtil;
import com.corereach.communication.component.ChatMsgInfoComponent;
import com.corereach.communication.netty.comm.UserChannelRel;
import com.corereach.communication.netty.domain.ChatMessage;
import com.corereach.communication.netty.domain.DataContent;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 处理消息的handler，TextWebSocketFrame：是netty为webSocket专门处理文本的对象，frame是消息的载体
 * @Author ga.zhang
 * @Date 2019/12/17 9:38
 * @Version V1.0
 **/
@Slf4j
@Component
@ChannelHandler.Sharable
public class WebSocketChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Resource
    private ChatMsgInfoComponent chatMsgInfoComponent;

    /**
     * 用于记录和管理所有客户端的channel
     */
    private static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        /*1、拿到从客户端发送过来的消息*/
        String text = msg.text();
        log.info("收到前端数据：{}",text);
        DataContent dataContent = JsonUtil.jsonToPojo(text, DataContent.class);
        if (!ObjectUtils.isEmpty(dataContent)) {
            Integer action = dataContent.getAction();

            /**获取当前channel*/
            Channel currentChannel = ctx.channel();

            /**2、判断消息类型，根据不同的消息做相应处理*/
            if (Constants.MESSAGE_ACTION_OF_CONNECT.equals(action)) {
                /**2.1  当websocket 第一次open的时候，初始化channel，把用的channel和userId关联起来*/
                String senderId = dataContent.getChatMessage().getSenderId();
                UserChannelRel.put(senderId, currentChannel);
                // 测试
                for (Channel c : users) {
                    System.out.println(c.id().asLongText());
                }
                UserChannelRel.output();
            } else if (Constants.MESSAGE_ACTION_OF_CHAT.equals(action)) {
                /**2.2  聊天类型的消息，把聊天记录保存到数据库，同时标记消息的签收状态[未签收]*/
                ChatMessage chatMessage = dataContent.getChatMessage();
                ChatMsgInfoDTO chatMsgInfoDTO = packChatMsgInfoDTO(chatMessage);
                String messageId = chatMsgInfoComponent.insert(chatMsgInfoDTO);
                chatMessage.setMessageId(messageId);
                DataContent data = new DataContent();
                data.setChatMessage(chatMessage);
                /**将消息发送给接收方*/
                Channel receiverChannel = UserChannelRel.get(chatMessage.getReceiverId());
                if (!ObjectUtils.isEmpty(receiverChannel)) {
                    Channel channel = users.find(receiverChannel.id());
                    if (!ObjectUtils.isEmpty(channel)){
                        receiverChannel.writeAndFlush(new TextWebSocketFrame(JsonUtil.objectToJson(data)));
                    }
                }
            } else if (Constants.MESSAGE_ACTION_OF_SIGNED.equals(action)) {
                /**2.3  签收消息类型，针对具体的消息进行签收，修改数据库中对应消息的签收状态[已签收]*/
                String extend = dataContent.getExtend();
                String[] split = extend.split(",");
                List<String> messageIdList = new ArrayList<>();
                for (String s : split) {
                    if (!StringUtils.isEmpty(s)) {
                        messageIdList.add(s);
                    }
                }
                if (!ObjectUtils.isEmpty(messageIdList) && messageIdList.size() > 0) {
                    Boolean isUpdated = chatMsgInfoComponent.updateChatMsgInfoToSignedByMessageId(messageIdList);
                }
            } else if (Constants.MESSAGE_ACTION_OF_KEEPALIVE.equals(action)) {
                /**2.4  心跳类型的消息*/
                System.out.println("收到来自channel为[" + currentChannel + "]的心跳包...");
                log.info("收到来自channel为{}的心跳包...",currentChannel);
            } else if (Constants.MESSAGE_ACTION_OF_PULL_FRIEND.equals(action)) {
                /***/
            }

        }
    }

    /**
     * 客户端连接时调用,当客户端连接到服务端之后，获取到客户端的channel，并放到ChannelGroup中去管理
     *
     * @param ctx ChannelHandlerContext
     * @throws Exception 异常
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
        super.handlerAdded(ctx);
    }

    /**
     * 客户端离开时调用
     *
     * @param ctx ChannelHandlerContext
     * @throws Exception 异常
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        /*当客户端离开时，会执行handlerRemoved，同时会自动的将ChannelGroup中客户端对应的channel移除，所以下面移除的代码可以不用写*/
        /*clients.remove(ctx.channel());*/
        /*打印长id和短id：长id是唯一的不重复的，短id在系统比较庞大时可能会重复*/
        log.info("{}:已经离开", ctx.channel().id().asLongText());
        log.info("{}:已经离开", ctx.channel().id().asShortText());
        super.handlerRemoved(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        /**发生异常后先关闭channel，再将channel从ChannelGroup中移除*/
        ctx.close();
        users.remove(ctx.channel());
    }

    private ChatMsgInfoDTO packChatMsgInfoDTO(ChatMessage chatMessage) {
        ChatMsgInfoDTO chatMsgInfoDTO = new ChatMsgInfoDTO();
        chatMsgInfoDTO.setSendUserId(chatMessage.getSenderId());
        chatMsgInfoDTO.setAcceptUserId(chatMessage.getReceiverId());
        chatMsgInfoDTO.setMsg(chatMessage.getMessage());
        return chatMsgInfoDTO;
    }
}
