package com.corereach.communication.dal.mapper;

import com.corereach.communication.dal.domain.ChatMsgInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ga.zhang
 * @date 2020/2/18 20:37:35
 */
@Mapper
public interface ChatMsgInfoMapper {

    /**
     * 新增聊天记录
     * @param record 聊天记录
     * @return Integer
     */
    Integer insert(ChatMsgInfo record);

    /**
     * 根据消息编号修改消息状态为已读
     * @param messageIdList 消息编号列表
     * @return Integer
     */
    Integer updateChatMsgInfoToSignedByMessageId(@Param("messageIdList") List<String> messageIdList);

    /**
     * 根据消息编号获取消息
     * @param id 消息编号
     * @return ChatMsgInfo
     */
    ChatMsgInfo getChatMsgInfoById(@Param("id") String id);

}