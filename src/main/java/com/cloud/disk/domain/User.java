package com.cloud.disk.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/9
 * Time: 11:33
 */
@Entity
@Table(name = "user")
public class User implements Serializable {

    /**
     * 用户Id
     */
    @Id
    private Long userId;

    /**
     * 用户名
     */
    @Column(length = 16)
    private String username;

    /**
     * 用户密码,加密
     */
    @Column(length = 32)
    private String password;

    /**
     * 加盐
     */
    @Column(length = 6)
    private String salt;

    /**
     * 用户邮箱
     */
    @Column(length = 64, unique = true)
    private String email;

    /**
     * 用户锁定
     */
    @Column()
    private boolean isLock = false;

    /**
     * 用户信息关联表
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_info_id", unique = true, nullable = false)
    private UserInfo userInfo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    public Set<File> files;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Set<File> getFiles() {
        return files;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }
}
