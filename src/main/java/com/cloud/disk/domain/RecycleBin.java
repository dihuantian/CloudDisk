package com.cloud.disk.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/9
 * Time: 20:45
 */
@Entity
public class RecycleBin implements Serializable {
    @Id
    private Long recycleId;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    @Column(nullable = false)
    private Date deleteDateTime;

    @Column(nullable = false)
    private String surplusDateTime;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Long getRecycleId() {
        return recycleId;
    }

    public void setRecycleId(Long recycleId) {
        this.recycleId = recycleId;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Date getDeleteDateTime() {
        return deleteDateTime;
    }

    public void setDeleteDateTime(Date deleteDateTime) {
        this.deleteDateTime = deleteDateTime;
    }

    public String getSurplusDateTime() {
        return surplusDateTime;
    }

    public void setSurplusDateTime(String surplusDateTime) {
        this.surplusDateTime = surplusDateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
