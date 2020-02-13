package com.corereach.communication.component.impl;

import cn.hutool.core.lang.ObjectId;
import com.corereach.communication.common.comm.Constants;
import com.corereach.communication.common.domain.MyFriendsInfoDTO;
import com.corereach.communication.common.domain.UserInfoDTO;
import com.corereach.communication.common.utils.ConvertUtil;
import com.corereach.communication.component.MyFriendsInfoComponent;
import com.corereach.communication.dal.domain.MyFriendsInfo;
import com.corereach.communication.dal.domain.UserInfo;
import com.corereach.communication.dal.mapper.MyFriendsInfoMapper;
import com.icode.rich.comm.ServiceSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2020/2/13 20:59
 * @Version V1.0
 **/
@Component
public class MyFriendsInfoComponentImpl extends ServiceSupport implements MyFriendsInfoComponent {

    @Resource
    private MyFriendsInfoMapper myFriendsInfoMapper;

    @Override
    public Boolean insert(MyFriendsInfoDTO record) {
        record.setId(getUniqueId());
        record.setIsDeleted(Constants.IS_DELETED_FALSE);
        return Optional.ofNullable(myFriendsInfoMapper.insert(ConvertUtil.convertDomain(MyFriendsInfo.class,record))).orElse(0) > 0;
    }

    @Override
    public MyFriendsInfoDTO selectMyFriendsInfoByUserId(String myUserId, String myFriendUserId) {
        MyFriendsInfo myFriendsInfo = Optional
                .ofNullable(myFriendsInfoMapper.selectMyFriendsInfoByUserId(myUserId, myFriendUserId)).orElse(new MyFriendsInfo());
        return ConvertUtil.convertDomain(MyFriendsInfoDTO.class,myFriendsInfo);
    }

    @Override
    public List<UserInfoDTO> listUserInfoWithFriendsInfo(String myUserId) {
        List<UserInfo> userInfoList = Optional.ofNullable(myFriendsInfoMapper.listUserInfoWithFriendsInfo(myUserId)).orElse(new ArrayList<>());
        List<UserInfoDTO> userInfoDTOList = new ArrayList<>();
        userInfoList.forEach(item -> userInfoDTOList.add(ConvertUtil.convertDomain(UserInfoDTO.class,item)));
        return userInfoDTOList;
    }

    private String getUniqueId() {
        Boolean flag = Boolean.TRUE;
        String id = null;
        while (flag) {
            id = ObjectId.next();
            MyFriendsInfo myFriendsInfo = myFriendsInfoMapper.selectByPrimaryKey(id);
            if (ObjectUtils.isEmpty(myFriendsInfo)) {
                flag = Boolean.FALSE;
            }
        }
        return id;
    }

}
