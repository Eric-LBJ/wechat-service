package com.corereach.communication.component.impl;

import cn.hutool.core.lang.ObjectId;
import com.corereach.communication.common.comm.ChatCode;
import com.corereach.communication.common.comm.Constants;
import com.corereach.communication.common.domain.ChatMsgInfoDTO;
import com.corereach.communication.common.utils.ConvertUtil;
import com.corereach.communication.component.ChatMsgInfoComponent;
import com.corereach.communication.dal.domain.ChatMsgInfo;
import com.corereach.communication.dal.mapper.ChatMsgInfoMapper;
import com.icode.rich.comm.ServiceSupport;
import com.icode.rich.exception.AiException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2020/2/18 20:52
 * @Version V1.0
 **/
@Component
public class ChatMsgInfoComponentImpl extends ServiceSupport implements ChatMsgInfoComponent {

    @Resource
    private ChatMsgInfoMapper chatMsgInfoMapper;

    @Override
    public Boolean updateChatMsgInfoToSignedByMessageId(List<String> messageIdList) {
        if (!ObjectUtils.isEmpty(messageIdList) && messageIdList.size() > 0){
            messageIdList.forEach(item -> {
                ChatMsgInfo chatMsgInfo = chatMsgInfoMapper.getChatMsgInfoById(item);
                if (ObjectUtils.isEmpty(chatMsgInfo) || StringUtils.isEmpty(chatMsgInfo.getId())) {
                    throw new AiException(Constants.isGlobal, ChatCode.CHAT_MESSAGE_NOT_EXIST);
                }
            });
            return messageIdList.size() == chatMsgInfoMapper.updateChatMsgInfoToSignedByMessageId(messageIdList);
        }
        return Boolean.TRUE;
    }

    @Override
    public List<ChatMsgInfoDTO> listUnReadChatMsgByAcceptUserId(String acceptUserId) {
        List<ChatMsgInfo> chatMsgInfoList = chatMsgInfoMapper.listUnReadChatMsgByAcceptUserId(acceptUserId);
        List<ChatMsgInfoDTO> chatMsgInfoDTOList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(chatMsgInfoList)){
            chatMsgInfoList.forEach(item -> chatMsgInfoDTOList.add(ConvertUtil.convertDomain(ChatMsgInfoDTO.class,item)));
        }
        return chatMsgInfoDTOList;
    }
}