package com.cloud.disk.web.controller;

import com.cloud.disk.service.FolderOperationService;
import com.cloud.disk.situation.ClassificationEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/16
 * Time: 0:59
 * 云盘文件夹操作
 */
@Controller
@RequestMapping(value = "/folder")
public class FolderController {

    @Autowired
    private FolderOperationService folderOperationService;

    /**
     * 获取指定文件夹中的所有文件内容,包括文件夹
     *
     * @param id 文件夹Id
     * @return
     */
    @GetMapping(value = "/id/{id}")
    public String fils(@PathVariable Long id, ModelMap map) {
        map.addAttribute("folders", folderOperationService.getChildFolder(id));
        map.addAttribute("files", folderOperationService.getChildFolderContent(id));
        map.addAttribute("folderId", id);
        map.addAttribute("type", "create");
        map.addAttribute("location", "children");
        map.addAttribute("parent", folderOperationService.getCurrentLocation(id));
        map.addAttribute("currentFolder", folderOperationService.getCurrentFolder(id));
        return "index";
    }

    /**
     * 删除文件夹
     *
     * @param id 删除的文件夹id
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/delete/id/{id}")
    public String deleteFolder(@PathVariable long id) {
        return null;
    }

    /**
     * 在当前文件夹下添加新的文件夹
     *
     * @param folderName   新文件夹名称
     * @param parentFolder 当前所在文件夹id
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/add")
    public String addFolder(String folderName, Long parentFolder) {
        return folderOperationService.addNewFolder(folderName, parentFolder);
    }
}
