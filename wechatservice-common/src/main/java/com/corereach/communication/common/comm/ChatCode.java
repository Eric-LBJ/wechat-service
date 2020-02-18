package com.corereach.communication.common.comm;

import com.icode.rich.comm.AiCode;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2019/12/23 13:40
 * @Version V1.0
 **/
public class ChatCode {

    /**
     * user : 500100-500399
     */
    public static final AiCode USER_INFO_NOT_EXIST = new AiCode(500100, "The user does not exist", "用户信息不存在");
    public static final AiCode PASSWORD_ERROR = new AiCode(500101, "The password error", "密码错误");
    public static final AiCode USER_REGISTER_FAILURE = new AiCode(500102, "The user register failure", "用户注册失败");
    public static final AiCode USER_NOT_EXIST = new AiCode(500103, "The user does not exist, can not update", "用户信息不存在，不能进行修改操作");
    public static final AiCode FACE_IMAGE_UPLOAD_FAILURE = new AiCode(500104, "The user face image upload failure", "用户头像上传失败");
    public static final AiCode USER_INFO_UPDATE_FAILURE = new AiCode(500105, "The user info update failure", "用户信息修改失败");
    public static final AiCode USER_IS_YOURSELF = new AiCode(500106, "The user is yourself", "您所搜索的用户就是自己，不能添加自己为好友哦");
    public static final AiCode USER_IS_ALREADY_YOUR_FRIEND = new AiCode(500107, "The user is already your friends", "您所搜索的用户已经是您的好友了，不能重复添加哦");
    public static final AiCode USER_LOGIN_FAILURE = new AiCode(500108, "The user login failure", "登录失败");
    public static final AiCode USERNAME_OR_PASSWORD_ERROR = new AiCode(500109, "The username or password error", "用户名或密码错误");
    public static final AiCode REDIS_USER_INFO_UPDATE_FAILURE = new AiCode(500110, "The user in redis info update failure", "用户信息缓存修改失败");

    /**
     * user : 500400-500799
     */
    public static final AiCode ADD_FRIEND_REQUEST_ALREADY_EXIST = new AiCode(500401, "You've already sent the user a friend request", "您已经向该用户发送过好友请求了，请耐心等待");;
    public static final AiCode OPERATOR_FAILURE = new AiCode(500402, "Operator failure", "操作失败");

    /**
     * chatMsg : 500800-501200
     */
    public static final AiCode CHAT_MESSAGE_NOT_EXIST = new AiCode(500800, "The chat message does not exist，can not update", "该消息不存在，无法进行修改操作");;
}
