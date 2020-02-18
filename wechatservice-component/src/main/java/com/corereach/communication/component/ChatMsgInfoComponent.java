package com.corereach.communication.component;

import com.corereach.communication.common.domain.ChatMsgInfoDTO;

import java.util.List;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2020/2/18 20:52
 * @Version V1.0
 **/
public interface ChatMsgInfoComponent {

    /**
     * 新增聊天记录
     * @param record 聊天记录
     * @return Integer
     */
    String insert(ChatMsgInfoDTO record);

    /**
     * 根据消息编号修改消息状态为已读
     * @param messageIdList 消息编号列表
     * @return Integer
     */
    Boolean updateChatMsgInfoToSignedByMessageId(List<String> messageIdList);

}
