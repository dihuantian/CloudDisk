package com.cloud.disk.service.impl;

import cn.izern.sequence.Sequence;
import com.cloud.disk.domain.File;
import com.cloud.disk.domain.RecycleBin;
import com.cloud.disk.domain.User;
import com.cloud.disk.repository.FileRepository;
import com.cloud.disk.repository.RecycleBinRepository;
import com.cloud.disk.service.FileOperationService;
import com.cloud.disk.situation.FileUploadEnum;
import com.cloud.disk.situation.UserLoginEnum;
import com.cloud.disk.unit.CronDateShift;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.collections.IteratorUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/27
 * Time: 8:23
 */
@Service
public class FileOperationServiceImp implements FileOperationService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private RecycleBinRepository recycleBinRepository;

    /**
     * 获取所有文件
     *
     * @return
     */
    @Override
    public List<File> getAllFile() {
        User user = getCurrentUser();
        if (user != null) {
            List<File> files = IteratorUtils.toList(fileRepository.findAll().iterator());
            files = files.stream().filter(u -> ((u.getFolder().getUser().getUserId()).equals(user.getUserId())) && u.getDeletes() == false).collect(Collectors.toList());
            return files;
        }
        return null;
    }

    /**
     * 获取指定类型的文件
     *
     * @param type
     * @return
     */
    @Override
    public List<File> getFileType(String type) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            User user = (User) subject.getPrincipal();
            List<File> typeFile = fileRepository.findAllByUserAndAndClassify(user, type);
            typeFile = typeFile.stream().filter(u -> u.getDeletes() == false).collect(Collectors.toList());
            if (typeFile != null)
                return typeFile;
        }
        return null;
    }

    /**
     * 下载文件服务
     *
     * @param fileId
     * @return
     */
    @Override
    public Map<String, Object> getFastFDFSFile(Long fileId) {
        if (fileId != 0) {
            Optional<File> optionalFile = fileRepository.findById(fileId);
            if (optionalFile.isPresent()) {
                Map<String, Object> map = new LinkedHashMap<>();
                File file = optionalFile.get();
                byte[] fileByte = fastFileStorageClient.downloadFile(file.getGroups(), file.getFdfsPath().replaceAll(file.getGroups() + "/", ""), new DownloadByteArray());
                map.put("file", file);
                map.put("content", fileByte);
                return map;
            }
        }
        return null;
    }

    /**
     * 文件删除或者还原的状态修改
     *
     * @param fileId
     * @param state
     * @return
     */
    @Override
    public String fileDeleteStat(Long fileId, int state) {
        User user = getCurrentUser();
        if (user != null) {
            Optional<File> optionalFile = fileRepository.findById(fileId);
            if (optionalFile.isPresent()) {
                if (!user.getUserId().equals(optionalFile.get().getUser().getUserId()))
                    return UserLoginEnum.USER_NOT_IS.getKey();
                File file = optionalFile.get();
                file.setDeletes(state == 0 ? false : true);
                RecycleBin recycleBin = null;
                if (state == 1) {
                    Calendar calendar = Calendar.getInstance(Locale.CHINA);
                    calendar.add(Calendar.DATE, 7);
                    recycleBin = new RecycleBin();
                    Sequence sequence = new Sequence();
                    recycleBin.setFile(file);
                    recycleBin.setDeleteDateTime(new Date());
                    recycleBin.setRecycleId(sequence.nextId());
                    recycleBin.setUser(user);
                    recycleBin.setSurplusDateTime(CronDateShift.dateSwitchCron(calendar.getTime()));
                    recycleBinRepository.save(recycleBin);
                } else {
                    recycleBin = recycleBinRepository.findAllByUser(user).stream().filter(u -> u.getFile().getFileId().equals(fileId)).findFirst().get();

                    recycleBin.setFile(file);
                    recycleBinRepository.delete(recycleBin);
                    return FileUploadEnum.FILE_REDUCTION_SUCCESS.getKey();
                }
            }
            return FileUploadEnum.FILE_DELETE_SUCCESS.getKey();
        }
        return null;
    }

    /**
     * 获取指定删除到回收站的文件
     *
     * @return
     */
    @Override
    public List<File> getDeleteFiles() {
        User user = getCurrentUser();
        if (user != null) {
            List<File> files = new ArrayList<>();
            List<RecycleBin> recycleBins = recycleBinRepository.findAllByUser(user);
            if (recycleBins != null) {
                files = recycleBins.stream().map(u -> u.getFile()).collect(Collectors.toList());
                return files;
            }
        }
        return null;
    }

    /**
     * 获取需要删除的回收站文件,由系统任务调度使用
     *
     * @return
     */
    @Override
    public List<RecycleBin> getDeleteRecycleBinFile() {
        List<RecycleBin> recycleBins = IteratorUtils.toList(recycleBinRepository.findAll().iterator());
        if (recycleBins != null) {
            return recycleBins;
        }
        return null;
    }

    /**
     * 由系统任务调度使用
     *
     * @param recycleBin
     */
    @Override
    public void deleteFile(RecycleBin recycleBin) {
        if (recycleBin != null) {
            recycleBinRepository.deleteById(recycleBin.getRecycleId());
            fastFileStorageClient.deleteFile(recycleBin.getFile().getFdfsPath());
            fileRepository.deleteById(recycleBin.getFile().getFileId());
        }
    }

    private User getCurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return (User) subject.getPrincipal();
        }
        return null;
    }

}
