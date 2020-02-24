package com.corereach.communication.service;

import com.corereach.communication.api.ChatMsgInfoService;
import com.corereach.communication.api.domain.vo.ChatMsgInfoVO;
import com.corereach.communication.common.comm.Constants;
import com.corereach.communication.common.domain.ChatMsgInfoDTO;
import com.corereach.communication.common.utils.ConvertUtil;
import com.corereach.communication.component.ChatMsgInfoComponent;
import com.icode.rich.comm.AiCodes;
import com.icode.rich.comm.AiResult;
import com.icode.rich.comm.RangeQueryResult;
import com.icode.rich.comm.ServiceSupport;
import com.icode.rich.exception.AiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2020/2/19 17:09
 * @Version V1.0
 **/
@Service("chatMsgInfoService")
public class ChatMsgInfoServiceImpl extends ServiceSupport implements ChatMsgInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FriendsRequestInfoServiceImpl.class);

    @Resource
    private ChatMsgInfoComponent chatMsgInfoComponent;

    @Override
    public AiResult<RangeQueryResult<ChatMsgInfoVO>> listUnReadChatMsgByAcceptUserId(String userId) {
        /**
         * 数据校验
         */
        super.notNull("用户编号", userId)
                .notBlank("用户编号", userId)
                .checkLength("用户编号", userId, 1, 64);
        try {
            List<ChatMsgInfoDTO> chatMsgInfoDTOList = chatMsgInfoComponent.listUnReadChatMsgByAcceptUserId(userId);
            RangeQueryResult<ChatMsgInfoVO> result = new RangeQueryResult<>();
            List<ChatMsgInfoVO> chatMsgInfoVOList = new ArrayList<>();
            if (!ObjectUtils.isEmpty(chatMsgInfoDTOList)){
                chatMsgInfoDTOList.forEach(item -> chatMsgInfoVOList.add(ConvertUtil.convertDomain(ChatMsgInfoVO.class,item)));
            }
            result.setData(chatMsgInfoVOList);
            result.setTotalNum((long)chatMsgInfoVOList.size());
            return new AiResult<>(Constants.isGlobal, result);
        } catch (AiException e) {
            LOGGER.error(e.getMessage(), e);
            return new AiResult<>(Constants.isGlobal, e.getCode(), e.getMessage(), e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new AiResult<>(Constants.isGlobal, AiCodes.SYSTEM_ERROR);
        }
    }

}
