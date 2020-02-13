package com.corereach.communication.component.impl;

import cn.hutool.core.lang.ObjectId;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.corereach.communication.common.comm.ChatCode;
import com.corereach.communication.common.comm.Constants;
import com.corereach.communication.common.domain.MyFriendsInfoDTO;
import com.corereach.communication.common.domain.UserInfoDTO;
import com.corereach.communication.common.utils.*;
import com.corereach.communication.component.MyFriendsInfoComponent;
import com.corereach.communication.component.RedisComponent;
import com.corereach.communication.component.UserInfoComponent;
import com.corereach.communication.dal.domain.MyFriendsInfo;
import com.corereach.communication.dal.domain.UserInfo;
import com.corereach.communication.dal.mapper.MyFriendsInfoMapper;
import com.corereach.communication.dal.mapper.UserInfoMapper;
import com.icode.rich.exception.AiException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Optional;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2019/12/23 14:59
 * @Version V1.0
 **/
@Component
public class UserInfoComponentImpl implements UserInfoComponent {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private MyFriendsInfoComponent myFriendsInfoComponent;

    @Resource
    private QRCodeUtils qrCodeUtils;

    @Resource
    private FastDFSClient fastDfsClient;

    @Resource
    private RedisComponent redisComponent;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public UserInfoDTO getUserInfoByUserId(String userId) {
        return ConvertUtil.convertDomain(UserInfoDTO.class,
                Optional.ofNullable(userInfoMapper.selectByUserId(userId)).orElse(new UserInfo()));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public Boolean usernameIsExist(String username) {
        UserInfo userInfo = userInfoMapper.selectUserInfoByUsername(username);
        return !ObjectUtils.isEmpty(userInfo) && !StringUtils.isEmpty(userInfo.getUsername());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String registerOrLogin(UserInfoDTO userInfoDTO) {
        /**
         * 判断用户是否存在，如果存在则走登录流程，如果不存在则走注册流程
         */
        UserInfoDTO user;
        UserInfo userInfo = userInfoMapper.selectUserInfoByUsername(userInfoDTO.getUsername());
        if (!ObjectUtils.isEmpty(userInfo) && !StringUtils.isEmpty(userInfo.getId())) {
            UserInfo userInfoWithPassword = userInfoMapper
                    .selectUserInfoByUsernameAndPassWord(userInfoDTO.getUsername(), Md5Util.getMd5Str(userInfoDTO.getPassword()));
            if (ObjectUtils.isEmpty(userInfoWithPassword) || StringUtils.isEmpty(userInfoWithPassword.getId())) {
                throw new AiException(Constants.isGlobal, ChatCode.PASSWORD_ERROR);
            }
            user = ConvertUtil.convertDomain(UserInfoDTO.class, userInfoWithPassword);

        } else {
            user = insertUser(userInfoDTO);
        }

        /**
         * 获取到用户信息后将用户信息缓存到redis，解决分布式应用用户信息存储问题
         */
        String onLineKey = userInfoDTO.getUsername() + Constants.ISOLATION + Constants.LOGIN_SIGN + Constants.ISOLATION
                + Constants.ON_LINE + Constants.ISOLATION;
        String token = IdUtil.simpleUUID();
        String tokenKey = token + Constants.ISOLATION + Constants.ISOLATION + Constants.ISOLATION;
        Boolean setOnLineKey = redisComponent.set(onLineKey, token, Constants.TOKEN_EXPIRES_TIME);
        Boolean setTokenKey = redisComponent.set(tokenKey, JSONObject.toJSONString(user), Constants.TOKEN_EXPIRES_TIME);
        if (!(setOnLineKey && setTokenKey)) {
            throw new AiException(Constants.isGlobal, ChatCode.USER_LOGIN_FAILURE);
        }
        return token;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public UserInfoDTO checkPassword(String username, String password) {
        UserInfo userInfo = Optional
                .ofNullable(userInfoMapper.selectUserInfoByUsernameAndPassWord(username, Md5Util.getMd5Str(password))).orElse(new UserInfo());
        if (ObjectUtils.isEmpty(userInfo) || StringUtils.isEmpty(userInfo.getId())) {
            throw new AiException(Constants.isGlobal, ChatCode.USERNAME_OR_PASSWORD_ERROR);
        }
        return ConvertUtil.convertDomain(UserInfoDTO.class, userInfo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserInfoDTO insertUser(UserInfoDTO userInfoDTO) {
        userInfoDTO.setNickName(userInfoDTO.getUsername());
        userInfoDTO.setFaceImage("");
        userInfoDTO.setFaceImageBig("");
        userInfoDTO.setPassword(Md5Util.getMd5Str(userInfoDTO.getPassword()));
        userInfoDTO.setId(getUniqueUserId());
        userInfoDTO.setIsDeleted(Constants.IS_DELETED_FALSE);
        userInfoDTO.setQrcode(getQrCode(userInfoDTO));
        if (userInfoMapper.insert(ConvertUtil.convertDomain(UserInfo.class, userInfoDTO)) <= 0) {
            throw new AiException(Constants.isGlobal, ChatCode.USER_REGISTER_FAILURE);
        }
        return userInfoDTO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserInfoDTO updateUserInfo(UserInfoDTO userInfoDTO) {
        /**更新前先判断用户是否存在*/
        UserInfo userInfo = userInfoMapper.selectByUserId(userInfoDTO.getId());
        if (ObjectUtils.isEmpty(userInfo) || StringUtils.isEmpty(userInfo.getId())) {
            throw new AiException(Constants.isGlobal, ChatCode.USER_NOT_EXIST);
        }

        /**如果用户头像数据不为空，将用户头像数据上传到fastDFS服务器*/
        if (!StringUtils.isEmpty(userInfoDTO.getFaceData())) {
            setFaceImage(userInfoDTO,userInfo);
            if (StringUtils.isEmpty(userInfoDTO.getFaceImage()) || StringUtils.isEmpty(userInfoDTO.getFaceImageBig())) {
                throw new AiException(Constants.isGlobal, ChatCode.FACE_IMAGE_UPLOAD_FAILURE);
            }
        }
        if (!StringUtils.isEmpty(userInfoDTO.getNickName())){
            userInfo.setNickName(userInfoDTO.getNickName());
        }

        /**未更新成功，抛出异常*/
        if (userInfoMapper.updateByUserId(userInfo) <= 0) {
            throw new AiException(Constants.isGlobal, ChatCode.USER_INFO_UPDATE_FAILURE);
        }

        /**更新用户信息后需要将缓存的用户信息也更细，并返回更新后的用户信息*/
        String onLineKey = userInfo.getUsername() + Constants.ISOLATION + Constants.LOGIN_SIGN + Constants.ISOLATION
                + Constants.ON_LINE + Constants.ISOLATION;
        String token = redisComponent.get(onLineKey);
        String tokenKey = token + Constants.ISOLATION + Constants.ISOLATION + Constants.ISOLATION;
        Boolean setTokenKey = redisComponent.set(tokenKey, JSONObject.toJSONString(userInfo), Constants.TOKEN_EXPIRES_TIME);
        if (!setTokenKey){
            throw new AiException(Constants.isGlobal, ChatCode.REDIS_USER_INFO_UPDATE_FAILURE);
        }
        return ConvertUtil.convertDomain(UserInfoDTO.class, userInfo);
    }

    @Override
    public UserInfoDTO searchUserInfoByUserName(String myUserId, String friendUsername) {
        /**
         * 1.需要判断用户是否存在
         * 2.需要判断用户是否是自己
         * 3.需要判断用户与自己是否已经是好友
         */
        UserInfo userInfo = userInfoMapper.selectUserInfoByUsername(friendUsername);
        if (ObjectUtils.isEmpty(userInfo) || StringUtils.isEmpty(userInfo.getId())) {
            throw new AiException(Constants.isGlobal, ChatCode.USER_INFO_NOT_EXIST);
        }
        if (myUserId.equals(userInfo.getId())) {
            throw new AiException(Constants.isGlobal, ChatCode.USER_IS_YOURSELF);
        }
        MyFriendsInfoDTO myFriendsInfoDTO = myFriendsInfoComponent.selectMyFriendsInfoByUserId(myUserId, userInfo.getId());
        if (!ObjectUtils.isEmpty(myFriendsInfoDTO) && !StringUtils.isEmpty(myFriendsInfoDTO.getId())) {
            throw new AiException(Constants.isGlobal, ChatCode.USER_IS_ALREADY_YOUR_FRIEND);
        }
        return ConvertUtil.convertDomain(UserInfoDTO.class, userInfo);
    }

    @Override
    public UserInfoDTO getUserInfoByUsername(String username) {
        return ConvertUtil.convertDomain(UserInfoDTO.class,
                Optional.ofNullable(userInfoMapper.selectUserInfoByUsername(username)).orElse(new UserInfo()));
    }

    private void setFaceImage(UserInfoDTO user, UserInfo userInfo) {
        try {
            /**获取前端传来的base64字符串转换成文件上传*/
            String base64Data = user.getFaceData();
            String userFaceImagePath = Constants.IMG_FILE_BASE_PATH + user.getId() + Constants.FACE_IMG_FILE_SUFFIX;
            /**将base64编码的图片转换成图片*/
            FileUtils.base64ToFile(userFaceImagePath, base64Data);

            /**上传文件到fastDFS服务器并返回链接*/
            MultipartFile faceImageFile = FileUtils.fileToMultipart(userFaceImagePath);
            String url = !ObjectUtils.isEmpty(faceImageFile) ? fastDfsClient.uploadBase64(faceImageFile) : Constants.DEFAULT_STRING_VALUE;

            /**获取缩略图的url*/
            String[] arr = url.split(Constants.SPILT_BASE);
            String thumpImgUrl = arr[0] + Constants.THUMP + arr[1];

            userInfo.setFaceImage(thumpImgUrl);
            userInfo.setFaceImageBig(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取二维码文件信息
     *
     * @param userInfoDTO 用户信息
     * @return MultipartFile
     */
    private String getQrCode(UserInfoDTO userInfoDTO) {
        String result = Constants.DEFAULT_STRING_VALUE;
        try {
            String qrCodeFilePath = Constants.IMG_FILE_BASE_PATH + userInfoDTO.getId() + Constants.QR_CODE_IMG_FILE_SUFFIX;
            String content = Constants.QR_CODE_PREFIX + userInfoDTO.getUsername() + Constants.QR_CODE_SUFFIX;
            qrCodeUtils.createQRCode(qrCodeFilePath, content);
            MultipartFile file = FileUtils.fileToMultipart(qrCodeFilePath);
            result = !ObjectUtils.isEmpty(file) ? fastDfsClient.uploadQRCode(file) : result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getUniqueUserId() {
        Boolean flag = Boolean.TRUE;
        String userId = null;
        while (flag) {
            userId = ObjectId.next();
            UserInfo userInfo = userInfoMapper.selectByUserId(userId);
            if (ObjectUtils.isEmpty(userInfo)) {
                flag = Boolean.FALSE;
            }
        }
        return userId;
    }

}
