package com.corereach.communication.dal.mapper;

import com.corereach.communication.dal.domain.ChatMsgInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMsgInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(ChatMsgInfo record);

    ChatMsgInfo selectByPrimaryKey(String id);

    List<ChatMsgInfo> selectAll();

    int updateByPrimaryKey(ChatMsgInfo record);
}