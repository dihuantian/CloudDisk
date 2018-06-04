package com.cloud.disk.situation;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/6/2
 * Time: 12:39
 */
public enum ShareEnum {

    SHARE_NOT_EXIT("SHARE_NOT_EXIT", "404"),
    SHARE_PASSWORD_ERROR("SHARE_PASSWORD_ERROR", "提取密码错误!"),
    SHARE_VERIFY_THROUGH("SHARE_VERIFY_THROUGH", "验证已经通过!"),
    SHARE_ALREADY_FILE("SHARE_ALREADY_FILE", "文件已经分享!"),
    SHARE_FILE_SUCCESS("SHARE_FILE_SUCCESS", "文件分享成功!"),
    SHARE_FILE_NOT_VERIFY("SHARE_FILE_NOT_VERIFY", "分享文件访问没有验证!");

    private String key;
    private String message;

    private ShareEnum(String key, String message) {
        this.key = key;
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
