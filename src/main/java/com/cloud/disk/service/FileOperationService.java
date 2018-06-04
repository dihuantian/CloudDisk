package com.cloud.disk.service;

import com.cloud.disk.domain.File;
import com.cloud.disk.domain.RecycleBin;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/27
 * Time: 8:23
 */
public interface FileOperationService {

    List<File> getAllFile();

    Map<String, Object> getFastFDFSFile(Long fileId);

    List<File> getFileType(String type);

    String fileDeleteStat(Long fileId, int state);

    List<File> getDeleteFiles();

    List<RecycleBin> getDeleteRecycleBinFile();

    void deleteFile(RecycleBin recycleBin);

}
