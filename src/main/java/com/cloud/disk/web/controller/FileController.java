package com.cloud.disk.web.controller;

import com.cloud.disk.domain.File;
import com.cloud.disk.domain.User;
import com.cloud.disk.service.FileOperationService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/16
 * Time: 13:49
 * 操作文件控制器
 */
@Controller
@RequestMapping(value = "/file")
public class FileController {

    @Autowired
    private FileOperationService fileOperationService;

    /**
     * 文件下载
     *
     * @param id 文件id
     * @return
     */
    @GetMapping(value = "/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        Map<String, Object> map = fileOperationService.getFastFDFSFile(id);
        if (map != null) {
            HttpHeaders headers = new HttpHeaders();
            File file = (File) map.get("file");
            byte[] content = (byte[]) map.get("content");
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFileName()));
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(content.length)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(content);
        }
        return null;
    }

    @GetMapping(value = "/delete/{state}/{id}")
    @ResponseBody
    public String deleteFile(@PathVariable Long id,@PathVariable Integer state) {
        return fileOperationService.fileDeleteStat(id,state);
    }

}
