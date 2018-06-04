package com.cloud.disk.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/9
 * Time: 15:30
 */
@Entity
public class File implements Serializable {

    /**
     * 文件Id
     */
    @Id
    private Long fileId;

    /**
     * 原始文件名
     */
    @Column(length = 64, nullable = false)
    private String fileName;

    /**
     * 文件后缀
     */
    @Column(length = 16, nullable = false)
    private String postfix;

    /**
     * 文件大小
     */
    @Column(nullable = false)
    private long size = 0l;

    /**
     * 文件的MIME
     */
    @Column(length = 16, nullable = false)
    private String mimes;

    /**
     * 文件的分类
     */
    @Column(length = 16, nullable = false)
    private String classify;

    /**
     * 文件上传时间
     */
    @Column(nullable = false)
    private Date upDateTime;

    /**
     * 文件是否分享
     */
    @Column
    private boolean share = false;

    /**
     * 文件是否已经删除
     */
    @Column(nullable = false)
    private boolean deletes = false;

    /**
     * 分组名称
     */
    @Column(length = 16, nullable = false)
    private String groups;

    /**
     * 文件保存在FDFS上的路径或者说Id
     */
    @Column(nullable = false)
    private String fdfsPath;

    /**
     * 文件保存在FDFS的文件名
     */
    @Column(nullable = false)
    private String fdfsFileName;

    /*
     * 所属文件夹
     * */
    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getMimes() {
        return mimes;
    }

    public void setMimes(String mimes) {
        this.mimes = mimes;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public Date getUpDateTime() {
        return upDateTime;
    }

    public void setUpDateTime(Date upDateTime) {
        this.upDateTime = upDateTime;
    }

    public boolean getShare() {
        return share;
    }

    public void setShare(boolean share) {
        this.share = share;
    }

    public boolean getDeletes() {
        return deletes;
    }

    public void setDeletes(boolean deletes) {
        this.deletes = deletes;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getFdfsPath() {
        return fdfsPath;
    }

    public void setFdfsPath(String fdfsPath) {
        this.fdfsPath = fdfsPath;
    }

    public String getFdfsFileName() {
        return fdfsFileName;
    }

    public void setFdfsFileName(String fdfsFileName) {
        this.fdfsFileName = fdfsFileName;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
