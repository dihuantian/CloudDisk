package com.cloud.disk.web.controller;

import com.cloud.disk.domain.File;
import com.cloud.disk.domain.RecycleBin;
import com.cloud.disk.domain.Share;
import com.cloud.disk.service.FileOperationService;
import com.cloud.disk.service.FolderOperationService;
import com.cloud.disk.service.LoginService;
import com.cloud.disk.service.ShareService;
import com.cloud.disk.situation.ClassificationEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/15
 * Time: 18:30
 * 云盘界面
 */
@Controller
@RequestMapping(value = "/disk")
public class DiskController {

    @Autowired
    private FolderOperationService folderOperationService;

    @Autowired
    private FileOperationService fileOperationService;

    @Autowired
    private ShareService shareService;

    /**
     * 所有文件夹或者文件视图
     *
     * @return
     */
    @GetMapping(value = "/all")
    public String allFiles(HttpSession session, ModelMap map) {
        map.addAttribute("folders", folderOperationService.getAllFolder());
        map.addAttribute("folderId", 0);
        map.addAttribute("type", "create");
        map.addAttribute("location", "index");
        return "index";
    }

    /**
     * 返回所有的音乐文件
     *
     * @return
     */
    @GetMapping(value = "/musics")
    public String allMusic(ModelMap map) {
        map.addAttribute("files", fileOperationService.getFileType(ClassificationEnum.MUSIC.getKey()));
        map.addAttribute("type", ClassificationEnum.MUSIC.getKey());
        return "index";
    }

    /**
     * 返回所有的视频文件
     *
     * @return
     */
    @GetMapping(value = "/videos")
    public String allVideo(ModelMap map) {
        map.addAttribute("files", fileOperationService.getFileType(ClassificationEnum.VIDEO.getKey()));
        map.addAttribute("type", ClassificationEnum.VIDEO.getKey());
        return "index";
    }

    /**
     * 返回所有的文档文件
     *
     * @return
     */
    @GetMapping(value = "/documents")
    public String allDocument(ModelMap map) {
        map.addAttribute("files", fileOperationService.getFileType(ClassificationEnum.DOCUMENT.getKey()));
        map.addAttribute("type", ClassificationEnum.DOCUMENT.getKey());
        return "index";
    }

    /**
     * 返回没有明确类型的其他文件
     *
     * @return
     */
    @GetMapping(value = "/rests")
    public String allRests(ModelMap map) {
        map.addAttribute("files", fileOperationService.getFileType(ClassificationEnum.OTHER.getKey()));
        map.addAttribute("type", ClassificationEnum.OTHER.getKey());
        return "index";
    }

    /**
     * 返回回收站中的所有文件
     *
     * @return
     */
    @GetMapping(value = "/recycle")
    public String recycle(ModelMap map) {
        map.addAttribute("files", fileOperationService.getDeleteFiles());
        map.addAttribute("state", "delete");
        return "index";
    }

    /**
     * 返回分享的所有文件
     *
     * @return
     */
    @GetMapping(value = "/share")
    public String share(ModelMap map) {
        Map<File, Share> fileRecycleBinMap = shareService.getFileShareMap();
        if (fileRecycleBinMap != null) {
            if (fileRecycleBinMap.size() > 0) {
                map.addAttribute("files", fileRecycleBinMap.keySet());
                map.addAttribute("shares", fileRecycleBinMap);
            }
        }
        return "index";
    }

    /**
     * 返回所有的图片文件
     *
     * @return
     */
    @GetMapping(value = "/image")
    public String image(ModelMap map) {
        map.addAttribute("files", fileOperationService.getFileType(ClassificationEnum.IMAGE.getKey()));
        map.addAttribute("type", ClassificationEnum.IMAGE.getKey());
        return "index";
    }
}
