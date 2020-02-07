package com.corereach.communication.dal.mapper;

import com.corereach.communication.dal.domain.FriendsRequestInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FriendsRequestInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(FriendsRequestInfo record);

    FriendsRequestInfo selectByPrimaryKey(String id);

    List<FriendsRequestInfo> selectAll();

    int updateByPrimaryKey(FriendsRequestInfo record);
}