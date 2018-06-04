package com.cloud.disk.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/9
 * Time: 14:33
 */
@Entity
public class FolderContact  implements Serializable {
    /**
     * 文件夹联系Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long contactId;

    /*
    * 父文件夹
    * */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Folder parent;

    /**
     * 子文件夹
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "child_id", nullable = false)
    private Folder folder;

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public Folder getParent() {
        return parent;
    }

    public void setParent(Folder parent) {
        this.parent = parent;
    }
}
