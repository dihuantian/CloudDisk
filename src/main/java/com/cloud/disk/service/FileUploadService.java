package com.cloud.disk.service;

import com.cloud.disk.config.upload.ProgressEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/18
 * Time: 16:09
 */
public interface FileUploadService {

    ProgressEntity getFileUploadProgress(HttpSession session);

    String fileStorage(List<MultipartFile> files,long folderId);
}
