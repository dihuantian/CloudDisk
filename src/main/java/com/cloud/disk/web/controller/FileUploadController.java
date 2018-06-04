package com.cloud.disk.web.controller;

import com.cloud.disk.config.upload.ProgressEntity;
import com.cloud.disk.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/16
 * Time: 13:54
 * 文件上传控制器
 */
@Controller
@RequestMapping(value = "/upload")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 文件上传
     *
     * @param files    上传文件参数
     * @param folderId 上传到指定文件夹中的文件夹Id
     * @return
     */
    @PostMapping(value = "/files")
    @ResponseBody
    public String upload(List<MultipartFile> files, @RequestParam(defaultValue = "0") Long folderId) {
        return fileUploadService.fileStorage(files, folderId);
    }

    /**
     * 获取文件上传进展=度
     *
     * @param session 根据回话获取文件上传进度信息
     * @return 返回进度信息Json
     */
    @GetMapping(value = "/progress")
    @ResponseBody
    public ProgressEntity progress(HttpSession session) {
        return fileUploadService.getFileUploadProgress(session);
    }

}
