package com.corereach.communication.component.impl;

import com.corereach.communication.common.comm.ChatCode;
import com.corereach.communication.common.comm.Constants;
import com.corereach.communication.common.domain.UserInfoDTO;
import com.corereach.communication.common.utils.ConvertUtil;
import com.corereach.communication.common.utils.FastDFSClient;
import com.corereach.communication.common.utils.FileUtils;
import com.corereach.communication.common.utils.QRCodeUtils;
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
    private MyFriendsInfoMapper myFriendsInfoMapper;

    @Resource
    private QRCodeUtils qrCodeUtils;

    @Resource
    private FastDFSClient fastDfsClient;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public UserInfoDTO getUserInfoByUserId(String userId) {
//        return ConvertUtil.convertDomain(UserInfoDTO.class,
//                Optional.ofNullable(userInfoMapper.selectByPrimaryKey(userId)).orElse(new UserInfo()));
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public Boolean usernameIsExist(String username) {
//        UserInfo info = new UserInfo();
//        info.setUsername(username);
//        info.setIsDeleted(Constants.IS_DELETED_FALSE);
//        UserInfo userInfo = userInfoMapper.selectOne(info);
//        return !ObjectUtils.isEmpty(userInfo) && !StringUtils.isEmpty(userInfo.getUsername());
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserInfoDTO registerOrLogin(UserInfoDTO userInfoDTO) {
        /**
         * 判断用户是否存在，如果存在则走登录流程，如果不存在则走注册流程
         */
        UserInfoDTO result;
        if (usernameIsExist(userInfoDTO.getUsername())) {
            result = checkPassword(userInfoDTO.getUsername(), userInfoDTO.getPassword());
        } else {
            result = insertUser(userInfoDTO);
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public UserInfoDTO checkPassword(String username, String password) {
//        Example userExample = new Example(UserInfo.class);
//        Example.Criteria criteria = userExample.createCriteria();
//        criteria.andEqualTo("username", username);
//        criteria.andEqualTo("password", Md5Util.getMd5Str(password));
//        UserInfo userInfo = Optional.ofNullable(userInfoMapper.selectOneByExample(userExample)).orElse(new UserInfo());
//        if (ObjectUtils.isEmpty(userInfo) || StringUtils.isEmpty(userInfo.getId())) {
//            throw new AiException(Constants.isGlobal, ChatCode.USERNAME_OR_PASSWORD_ERROR);
//        }
//        return ConvertUtil.convertDomain(UserInfoDTO.class, userInfo);
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserInfoDTO insertUser(UserInfoDTO userInfoDTO) {
//        String id = sid.nextShort();
//        userInfoDTO.setNickName(userInfoDTO.getUsername());
//        userInfoDTO.setFaceImage("");
//        userInfoDTO.setFaceImageBig("");
//        userInfoDTO.setPassword(Md5Util.getMd5Str(userInfoDTO.getPassword()));
//        userInfoDTO.setId(id);
//        userInfoDTO.setIsDeleted(0L);
//        userInfoDTO.setQrcode(getQrCode(userInfoDTO));
//        if (userInfoMapper.insert(ConvertUtil.convertDomain(UserInfo.class, userInfoDTO)) <= 0) {
//            throw new AiException(Constants.isGlobal, ChatCode.USER_REGISTER_FAILURE);
//        }
//        return userInfoDTO;
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UserInfoDTO updateUserInfo(UserInfoDTO userInfoDTO) {
        /**更新前先判断用户是否存在*/
//        if (!userInfoMapper.existsWithPrimaryKey(userInfoDTO.getId())) {
//            throw new AiException(Constants.isGlobal, ChatCode.USER_NOT_EXIST);
//        }
//
//        /**如果用户头像数据不为空，将用户头像数据上传到fastDFS服务器*/
//        if (!StringUtils.isEmpty(userInfoDTO.getFaceData())) {
//            setFaceImage(userInfoDTO);
//            if (StringUtils.isEmpty(userInfoDTO.getFaceImage()) || StringUtils.isEmpty(userInfoDTO.getFaceImageBig())) {
//                throw new AiException(Constants.isGlobal, ChatCode.FACE_IMAGE_UPLOAD_FAILURE);
//            }
//        }
//
//        /**更新用户信息并返回更新后的用户信息*/
//        UserInfo result = new UserInfo();
//        if (userInfoMapper.updateByPrimaryKeySelective(ConvertUtil.convertDomain(UserInfo.class, userInfoDTO)) > 0) {
//            result = userInfoMapper.selectByPrimaryKey(userInfoDTO.getId());
//        }
//
//        /**未更新成功，抛出异常*/
//        if (ObjectUtils.isEmpty(result) || StringUtils.isEmpty(result.getId())) {
//            throw new AiException(Constants.isGlobal, ChatCode.USER_INFO_UPDATE_FAILURE);
//        }
//        return ConvertUtil.convertDomain(UserInfoDTO.class, result);
        return null;
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
        if (myUserId.equals(userInfo.getId())){
            throw new AiException(Constants.isGlobal, ChatCode.USER_IS_YOURSELF);
        }
        MyFriendsInfo myFriendsInfo = myFriendsInfoMapper.selectMyFriendsInfoByUserId(myUserId,userInfo.getId());
        if (!ObjectUtils.isEmpty(myFriendsInfo) && !StringUtils.isEmpty(myFriendsInfo.getId())) {
            throw new AiException(Constants.isGlobal, ChatCode.USER_IS_ALREADY_YOUR_FRIEND);
        }
        return ConvertUtil.convertDomain(UserInfoDTO.class,userInfo);
    }

    private void setFaceImage(UserInfoDTO user) {
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

            user.setFaceImage(thumpImgUrl);
            user.setFaceImageBig(url);
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

}
