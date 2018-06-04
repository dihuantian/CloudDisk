package com.cloud.disk.domain;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/9
 * Time: 16:10
 */
@Entity
public class Share {
    /**
     * 分享文件记录Id
     */
    @Expose
    @Id
    private Long shareId;

    /**
     * 分享的文件
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", unique = true, nullable = false)
    private File file;

    /**
     * 分享路径
     */
    @Expose
    @Column(nullable = false,unique = true)
    private String sharePath;

    /**
     * 分享密码
     */
    @Expose
    @Column(length = 8,nullable = false)
    private String password;

    /**
     * 分享时间
     */
    @Expose
    @Column
    public Date shareDateTime;

    /**
     * 分享后下载次数
     */
    @Expose
    @Column
    public int number;

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getSharePath() {
        return sharePath;
    }

    public void setSharePath(String sharePath) {
        this.sharePath = sharePath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getShareDateTime() {
        return shareDateTime;
    }

    public void setShareDateTime(Date shareDateTime) {
        this.shareDateTime = shareDateTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
