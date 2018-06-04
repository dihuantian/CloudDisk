package com.cloud.disk.domain;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/9
 * Time: 14:26
 */
@Entity
public class Folder implements Serializable {
    /**
     * 文件夹Id
     */
    @Expose
    @Id
    private Long folderId;

    /**
     * 文件夹名称
     */
    @Expose
    @Column(length = 16, nullable = false)
    private String folderName;

    /**
     * 是否存在父文件夹
     */
    @Expose
    @Column(nullable = false)
    private boolean parentExit;

    /**
     * 文件夹创建时间
     */
    @Expose
    @Column(nullable = false)
    private Date createTime;

    /**
     * 文件夹下的子文件夹
     */
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "parent", fetch = FetchType.EAGER)
    private Set<FolderContact> folderContacts;

    /**
     * 文件夹所属用户
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 文件夹下的文件
     */
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "folder", fetch = FetchType.EAGER)
    private Set<File> files;

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Set<FolderContact> getFolderContacts() {
        return folderContacts;
    }

    public void setFolderContacts(Set<FolderContact> folderContacts) {
        this.folderContacts = folderContacts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<File> getFiles() {
        return files;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }

    public boolean getParentExit() {
        return parentExit;
    }

    public void setParentExit(boolean parentExit) {
        this.parentExit = parentExit;
    }
}
