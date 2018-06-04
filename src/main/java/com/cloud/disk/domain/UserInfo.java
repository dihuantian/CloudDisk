package com.cloud.disk.domain;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/9
 * Time: 11:33
 */
@Entity
public class UserInfo implements Serializable{
    /**
     * 用户信息Id
     */
    @Id
    private Long userInfoId;

    /**
     * 用户昵称
     */
    @Column(length = 16)
    private String nickname;

    /**
     * 用户性别
     */
    @Column
    private boolean sex = true;

    /**
     * 用户电话号码
     */
    @Column(length = 11)
    private String phoneNumber;

    /**
     * 用户地址
     */
    @Column(length = 128)
    private String address;

    /**
     * 用户信息简介
     */
    @Column
    private String intro;

    public Long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
