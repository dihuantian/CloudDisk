package com.cloud.disk.service;

import com.cloud.disk.domain.File;
import com.cloud.disk.domain.Folder;
import com.cloud.disk.domain.FolderContact;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/21
 * Time: 19:01
 */
public interface FolderOperationService {

    String uploadFolderName();

    String deleteFolder(long folderId);

    String addNewFolder(String folderName, long parentFolder);

    List<Folder> getAllFolder();

    List<Folder> getChildFolder(long folderId);

    List<File> getChildFolderContent(long folderId);

    FolderContact getCurrentLocation(long folderId);

    Folder getCurrentFolder(long folderId);
}
