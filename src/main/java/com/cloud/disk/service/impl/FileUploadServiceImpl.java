package com.cloud.disk.service.impl;

import cn.izern.sequence.Sequence;
import com.cloud.disk.config.upload.ProgressEntity;
import com.cloud.disk.domain.File;
import com.cloud.disk.domain.Folder;
import com.cloud.disk.domain.User;
import com.cloud.disk.repository.FileRepository;
import com.cloud.disk.repository.FolderRepository;
import com.cloud.disk.repository.UserRepository;
import com.cloud.disk.service.FileUploadService;
import com.cloud.disk.situation.ClassificationEnum;
import com.cloud.disk.situation.FileUploadEnum;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/18
 * Time: 16:10
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 返回upload file 进度信息
     *
     * @param session
     * @return 返回进度json信息, 用于确认是否上传成功
     */
    @Override
    public ProgressEntity getFileUploadProgress(HttpSession session) {
        System.out.println(session.getId());
        ProgressEntity status = (ProgressEntity) session.getAttribute("status");
        if (status != null) {
            return status;
        }
        return null;
    }

    /**
     * 将上传文件保存到FastFDFS中,同时保存到指定数据库文件夹中
     *
     * @param files
     * @param folderId
     * @return
     */
    @Override
    public String fileStorage(List<MultipartFile> files, long folderId) {
        try {
            if (SecurityUtils.getSubject().isAuthenticated()) {
                List<File> fileList = new ArrayList<>();
                for (MultipartFile file : files) {
                    File uploadFile = null;
                    Optional<Folder> folder = folderRepository.findById(folderId);
                    if (folder.isPresent()) {
                        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
                        uploadFile = encapsulationFileInfo(file, storePath, folderId);
                        uploadFile.setFolder(folder.get());
                    } else
                        return FileUploadEnum.FOLDER_NOT_EXIT.getKey();
                    fileList.add(uploadFile);
                }
                fileRepository.saveAll(fileList);
                return FileUploadEnum.FILE_UPLOAD_SUCCESS.getKey();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 封装上传文件的信息
     *
     * @return
     */
    private File encapsulationFileInfo(MultipartFile uploadFiles, StorePath storePath, long folderId) {
        if (!uploadFiles.isEmpty()) {
            Sequence sequence = new Sequence();
            String url = getAccessUrl(storePath);
            File file = new File();
            file.setFileId(sequence.nextId());
            file.setFileName(FilenameUtils.getName(uploadFiles.getOriginalFilename()));
            file.setUpDateTime(new Date());
            file.setMimes(FilenameUtils.getExtension(uploadFiles.getOriginalFilename()));
            file.setSize(uploadFiles.getSize());
            file.setFdfsFileName(FilenameUtils.getName(url));
            file.setClassify(classification(FilenameUtils.getExtension(uploadFiles.getOriginalFilename())));
            file.setFdfsPath(url);
            file.setUser((User) SecurityUtils.getSubject().getPrincipal());
            file.setGroups(storePath.getGroup());
            file.setPostfix(FilenameUtils.getExtension(url));
            return file;
        }
        return null;
    }

    /**
     * 获取上传后的路径信息
     *
     * @param storePath
     * @return
     */
    private String getAccessUrl(StorePath storePath) {
        String fileUrl = storePath.getFullPath();
        return fileUrl;
    }

    /**
     * 上传文件分类
     *
     * @param type
     * @return
     */
    private String classification(String type) {
        String[] musics = {"flac", "ape", "wav", "mp3", "aac", "ogg", "wma"};
        String[] video = {"3gp", "mp4", "rmvb", "wmv"};
        String[] image = {"png", "jpg", "jpeg"};
        String[] document = {"txt", "docx", "doc"};
        if (Arrays.asList(musics).contains(type))
            return ClassificationEnum.MUSIC.getKey();
        else if (Arrays.asList(video).contains(type))
            return ClassificationEnum.VIDEO.getKey();
        else if (Arrays.asList(image).contains(type))
            return ClassificationEnum.IMAGE.getKey();
        else if (Arrays.asList(document).contains(type))
            return ClassificationEnum.DOCUMENT.getKey();
        return ClassificationEnum.OTHER.getKey();
    }
}
