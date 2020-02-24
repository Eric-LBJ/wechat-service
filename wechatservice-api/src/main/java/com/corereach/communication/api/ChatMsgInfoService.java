package com.corereach.communication.api;

import com.corereach.communication.api.domain.vo.ChatMsgInfoVO;
import com.icode.rich.comm.AiResult;
import com.icode.rich.comm.RangeQueryResult;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2020/2/19 17:08
 * @Version V1.0
 **/
public interface ChatMsgInfoService {

    /**
     * 根据接受者编号获取接收者未读消息
     *
     * @param userId 接收者编号
     * @return AiResult
     */
    AiResult<RangeQueryResult<ChatMsgInfoVO>> listUnReadChatMsgByAcceptUserId(String userId);

}
