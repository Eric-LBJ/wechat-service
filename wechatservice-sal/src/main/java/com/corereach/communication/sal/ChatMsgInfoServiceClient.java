package com.corereach.communication.sal;

import cn.hutool.core.lang.ObjectId;
import com.corereach.communication.common.comm.ChatCode;
import com.corereach.communication.common.comm.Constants;
import com.corereach.communication.common.domain.ChatMsgInfoDTO;
import com.corereach.communication.common.utils.ConvertUtil;
import com.corereach.communication.dal.domain.ChatMsgInfo;
import com.corereach.communication.dal.mapper.ChatMsgInfoMapper;
import com.icode.rich.comm.ServiceSupport;
import com.icode.rich.exception.AiException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2020/2/18 20:52
 * @Version V1.0
 **/
@Component
public class ChatMsgInfoServiceClient extends ServiceSupport {

    @Resource
    private ChatMsgInfoMapper chatMsgInfoMapper;

    public String insert(ChatMsgInfoDTO record) {
        String id = getUniqueId();
        record.setId(id);
        Integer insert = chatMsgInfoMapper.insert(ConvertUtil.convertDomain(ChatMsgInfo.class, record));
        return insert > 0 ? id : null;
    }

    public void updateChatMsgInfoToSignedByMessageId(List<String> messageIdList) {
        if (!ObjectUtils.isEmpty(messageIdList) && messageIdList.size() > 0){
            messageIdList.forEach(item -> {
                ChatMsgInfo chatMsgInfo = chatMsgInfoMapper.getChatMsgInfoById(item);
                if (ObjectUtils.isEmpty(chatMsgInfo) || StringUtils.isEmpty(chatMsgInfo.getId())) {
                    throw new AiException(Constants.isGlobal, ChatCode.CHAT_MESSAGE_NOT_EXIST);
                }
            });
            chatMsgInfoMapper.updateChatMsgInfoToSignedByMessageId(messageIdList);
        }
    }

    private String getUniqueId() {
        Boolean flag = Boolean.TRUE;
        String id = null;
        while (flag) {
            id = ObjectId.next();
            ChatMsgInfo chatMsgInfo = chatMsgInfoMapper.getChatMsgInfoById(id);
            if (ObjectUtils.isEmpty(chatMsgInfo)) {
                flag = Boolean.FALSE;
            }
        }
        return id;
    }
}
