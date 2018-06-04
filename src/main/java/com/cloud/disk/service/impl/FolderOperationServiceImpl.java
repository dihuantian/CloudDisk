package com.cloud.disk.service.impl;

import cn.izern.sequence.Sequence;
import com.cloud.disk.domain.File;
import com.cloud.disk.domain.Folder;
import com.cloud.disk.domain.FolderContact;
import com.cloud.disk.domain.User;
import com.cloud.disk.repository.FolderContactRepository;
import com.cloud.disk.repository.FolderRepository;
import com.cloud.disk.repository.UserRepository;
import com.cloud.disk.service.FolderOperationService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.collections.IteratorUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/21
 * Time: 19:01
 */
@Service
public class FolderOperationServiceImpl implements FolderOperationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private FolderContactRepository folderContactRepository;

    @Override
    public String uploadFolderName() {
        return null;
    }

    @Override
    public String deleteFolder(long folderId) {
        return null;
    }

    /**
     * 在当前文件夹下创建新的文件夹
     *
     * @param folderName   新文件夹名称
     * @param parentFolder 父文件夹Id
     * @return 创建成功返回创建信息, 否则返回null
     */
    @Override
    public String addNewFolder(String folderName, long parentFolder) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            Sequence sequence = new Sequence();
            User user = userRepository.findById(((User) subject.getPrincipal()).getUserId()).get();
            Folder folder = new Folder();
            folder.setCreateTime(new Date());
            folder.setUser(user);
            folder.setFolderName(folderName);
            folder.setParentExit(parentFolder != 0 ? true : false);
            folder.setFolderId(sequence.nextId());
            folderRepository.save(folder);
            if (parentFolder != 0) {
                Folder parent = getCurrentFolder(parentFolder);
                FolderContact folderContact = new FolderContact();
                folderContact.setContactId(sequence.nextId());
                folderContact.setParent(parent);
                folderContact.setFolder(folder);
                folderContactRepository.save(folderContact);
            }
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            return gson.toJson(folder);
        }
        return null;
    }

    @Override
    public List<Folder> getAllFolder() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            User user = (User) subject.getPrincipal();
            List<Folder> folders = IteratorUtils.toList(folderRepository.findAllByUser(user).iterator());
            folders = folders.stream().filter(u -> u.getParentExit() == false).collect(Collectors.toList());
            if (folders != null && folders.size() > 0)
                return folders;
        }
        return null;
    }

    @Override
    public List<Folder> getChildFolder(long folderId) {
        Folder folder = getCurrentFolder(folderId);
        if (folder != null) {
            List<FolderContact> folderContacts = IteratorUtils.toList(folder.getFolderContacts().iterator());
            if (folderContacts != null) {
                return folderContacts.stream().map(u -> u.getFolder()).collect(Collectors.toList());
            }
        }
        return null;
    }

    @Override
    public List<File> getChildFolderContent(long folderId) {
        Folder folder = getCurrentFolder(folderId);
        if (folder != null) {
            List<File> files = IteratorUtils.toList(folder.getFiles().iterator());
            if (files != null) {
                files = files.stream().filter(u -> u.getDeletes() == false).collect(Collectors.toList());
                return files;
            }
        }
        return null;
    }

    /**
     * 找到当前文件夹的父文件夹
     *
     * @param folderId
     * @return
     */
    @Override
    public FolderContact getCurrentLocation(long folderId) {
        Optional<FolderContact> optionalContact = folderContactRepository.findAllByFolder_FolderId(folderId);
        if (optionalContact.isPresent()) {
            return optionalContact.get();
        }
        return null;
    }

    @Override
    public Folder getCurrentFolder(long folderId) {
        Optional<Folder> optionalFolder = folderRepository.findById(folderId);
        if (optionalFolder.isPresent()) {
            return optionalFolder.get();
        }
        return null;
    }
}
