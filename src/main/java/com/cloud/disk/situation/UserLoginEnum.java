package com.cloud.disk.situation;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/20
 * Time: 20:18
 */
public enum UserLoginEnum {

    REGISTER_CODE_ERROR("REGISTER_CODE_ERROR", "注册验证码错误"),
    REGISTER_EMAIL_EXIT("REGISTER_EMAIL_EXIT", "注册邮箱已存在"),
    REGISTER_SUCCESS("REGISTER_SUCCESS", "注册成功"),
    USER_LOGIN_STATUS("USER_LOGIN_STATUS", "用户处于登录状态"),
    USER_LOGIN_SUCCESS("USER_LOGIN_SUCCESS", "用户登录成功"),
    USER_LOGIN_CODE_ERROR("USER_LOGIN_CODE_ERROR", "用户登录验证码错误"),
    USER_LOGIN_PASS_ERROR("USER_LOGIN_PASS_ERROR", "用户登录密码错误"),
    USER_LOGIN_USER_ERROR("USER_LOGIN_USER_ERROR", "用户错误"),
    USER_NOT_IS("USER_NOT_IS","不是用户!"),
    USER_LOGOUT_SUCCESS("USER_LOGOUT_SUCCESS","用户注销成功!");
    private String key;
    private String message;

    private UserLoginEnum(String key, String message) {
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
