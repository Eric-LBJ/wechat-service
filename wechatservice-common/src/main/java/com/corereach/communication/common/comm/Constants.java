package com.corereach.communication.common.comm;

/**
 * @Description: TODO
 * @Author ga.zhang
 * @Date 2019/12/19 19:28
 * @Version V1.0
 **/
public class Constants {

    /**
     * 国际化
     */
    public static Boolean isGlobal = Boolean.FALSE;
    /**
     * 缩略图尾缀
     */
    public static final String THUMP = "_80x80.";
    /**
     * 图片文件放置的本地文件夹
     */
    public static final String IMG_FILE_BASE_PATH = "F:\\userImage\\";
    /**
     * 头像图片文件尾缀
     */
    public static final String FACE_IMG_FILE_SUFFIX = "userFaceImageBase64.png";
    /**
     * 二维码图片文件尾缀
     */
    public static final String QR_CODE_IMG_FILE_SUFFIX = "qrCode.png";
    /**
     * 分割符
     */
    public static final String SPILT_BASE = "\\.";
    /**
     * 默认字符串的值
     */
    public static final String DEFAULT_STRING_VALUE = "";
    /**
     * 二维码信息前缀
     */
    public static final String QR_CODE_PREFIX = "username:[";
    /**
     * 二维码信息尾缀
     */
    public static final String QR_CODE_SUFFIX = "]";
    /**
     * 是否删除：否
     */
    public static final Long IS_DELETED_FALSE = 0L;
    /**
     * 隔离符
     */
    public static final String ISOLATION = "|";
    /**
     * 登录标识
     */
    public static final String LOGIN_SIGN = "weChat";
    /**
     * 在线
     */
    public static final String ON_LINE = "onLine";
    /**
     * token失效时间：永不过期
     */
    public static final Integer TOKEN_EXPIRES_TIME = -1;

}
