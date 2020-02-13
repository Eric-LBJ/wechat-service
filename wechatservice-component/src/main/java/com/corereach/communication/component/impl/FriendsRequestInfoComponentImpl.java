package com.corereach.communication.component.impl;

import cn.hutool.core.lang.ObjectId;
import com.corereach.communication.common.comm.ChatCode;
import com.corereach.communication.common.comm.Constants;
import com.corereach.communication.common.domain.MyFriendsInfoDTO;
import com.corereach.communication.common.domain.UserInfoDTO;
import com.corereach.communication.common.utils.ConvertUtil;
import com.corereach.communication.component.FriendsRequestInfoComponent;
import com.corereach.communication.component.MyFriendsInfoComponent;
import com.corereach.communication.component.UserInfoComponent;
import com.corereach.communication.dal.domain.FriendsRequestInfo;
import com.corereach.communication.dal.domain.UserInfo;
import com.corereach.communication.dal.mapper.FriendsRequestInfoMapper;
import com.icode.rich.comm.ServiceSupport;
import com.icode.rich.exception.AiException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2020/2/13 18:44
 * @Version V1.0
 **/
@Component
public class FriendsRequestInfoComponentImpl extends ServiceSupport implements FriendsRequestInfoComponent {

    @Resource
    private FriendsRequestInfoMapper friendsRequestInfoMapper;

    @Resource
    private UserInfoComponent userInfoComponent;

    @Resource
    private MyFriendsInfoComponent myFriendsInfoComponent;

    @Override
    public Boolean addFriendsRequest(String myUserId, String friendUsername) {

        /**
         * 1、先判断用户信息是否存在
         */
        UserInfoDTO userInfoDTO = userInfoComponent.getUserInfoByUsername(friendUsername);
        if (ObjectUtils.isEmpty(userInfoDTO) || StringUtils.isEmpty(userInfoDTO.getId())) {
            throw new AiException(Constants.isGlobal, ChatCode.USER_INFO_NOT_EXIST);
        }

        /**
         * 2、判断是否已经向该用户发送过好友请求
         */
        FriendsRequestInfo friendsRequestInfo = friendsRequestInfoMapper.selectBySendAndAcceptUserId(myUserId, userInfoDTO.getId());
        if (!ObjectUtils.isEmpty(friendsRequestInfo) && !StringUtils.isEmpty(friendsRequestInfo.getId())) {
            throw new AiException(Constants.isGlobal, ChatCode.ADD_FRIEND_REQUEST_ALREADY_EXIST);
        }

        /**
         * 3、插入添加好友请求
         */
        FriendsRequestInfo requestInfo = new FriendsRequestInfo();
        requestInfo.setId(getUniqueId());
        requestInfo.setSendUserId(myUserId);
        requestInfo.setAcceptUserId(userInfoDTO.getId());
        requestInfo.setRequestDateTime(new Date());
        requestInfo.setIsDeleted(Constants.IS_DELETED_FALSE);
        Integer insert = friendsRequestInfoMapper.insert(requestInfo);
        return insert > 0;
    }

    @Override
    public List<UserInfoDTO> listUserInfoWithRequestInfo(String myUserId) {
        List<UserInfo> userInfoList = Optional.ofNullable(friendsRequestInfoMapper.listUserInfoWithRequestInfo(myUserId)).orElse(new ArrayList<>());
        List<UserInfoDTO> userInfoDTOList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(userInfoList)) {
            userInfoList.forEach(item -> userInfoDTOList.add(ConvertUtil.convertDomain(UserInfoDTO.class, item)));
        }
        return userInfoDTOList;
    }

    @Override
    public Boolean deleteFriendsRequestInfo(String sendUserId, String acceptUserId) {
        return Optional.ofNullable(friendsRequestInfoMapper.deleteFriendsRequestInfo(sendUserId, acceptUserId)).orElse(0) > 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<UserInfoDTO> passOrIgnoreRequest(String sendUserId, String acceptUserId, Integer operatorType) {
        Boolean result = Boolean.FALSE;
        if (Constants.OPERATOR_TYPE_OF_PASS.equals(operatorType)) {
            /**
             * 如果通过好友请求：
             * 1、向myFriends表插入两条数据，互为好友
             * 2、删除好友请求数据
             */
            Boolean insertFirst = myFriendsInfoComponent.insert(packageMyFriendInfo(sendUserId, acceptUserId));
            Boolean insertSecond = myFriendsInfoComponent.insert(packageMyFriendInfo(acceptUserId, sendUserId));
            Integer isDeleted = friendsRequestInfoMapper.deleteFriendsRequestInfo(sendUserId, acceptUserId);
            if (insertFirst && insertSecond && isDeleted > 0) {
                result = Boolean.TRUE;
            }
        } else if (Constants.OPERATOR_TYPE_OF_IGNORE.equals(operatorType)) {
            /**
             * 如果忽略好友请求，直接将好友请求数据删除
             */
            result = friendsRequestInfoMapper.deleteFriendsRequestInfo(sendUserId, acceptUserId) > 0;
        }

        if (!result){
            throw new AiException(Constants.isGlobal,ChatCode.OPERATOR_FAILURE);
        }

        return myFriendsInfoComponent.listUserInfoWithFriendsInfo(acceptUserId);
    }

    private String getUniqueId() {
        Boolean flag = Boolean.TRUE;
        String id = null;
        while (flag) {
            id = ObjectId.next();
            FriendsRequestInfo requestInfo = friendsRequestInfoMapper.selectById(id);
            if (ObjectUtils.isEmpty(requestInfo)) {
                flag = Boolean.FALSE;
            }
        }
        return id;
    }

    private MyFriendsInfoDTO packageMyFriendInfo(String userIdFirst, String userIdSecond) {
        MyFriendsInfoDTO myFriendsInfoDTO = new MyFriendsInfoDTO();
        myFriendsInfoDTO.setMyUserId(userIdFirst);
        myFriendsInfoDTO.setMyFriendUserId(userIdSecond);
        return myFriendsInfoDTO;
    }
}
