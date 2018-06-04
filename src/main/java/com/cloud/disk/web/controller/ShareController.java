package com.cloud.disk.web.controller;

import com.cloud.disk.domain.File;
import com.cloud.disk.service.FileOperationService;
import com.cloud.disk.service.ShareService;
import com.cloud.disk.situation.ShareEnum;
import com.cloud.disk.situation.UserLoginEnum;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/6/2
 * Time: 8:29
 */
@Controller
@RequestMapping(value = "/share")
public class ShareController {

    @Autowired
    private ShareService shareService;

    @GetMapping(value = "/page/{shareId}")
    public String share(@PathVariable Long shareId, HttpSession session, ModelMap map) {
        if (shareService.getThoughVerifyPassword(session, shareId) != null)
            return "redirect:/error";
        map.addAttribute("shareId", shareId);
        if (shareService.shareExit(shareId))
            return "share";
        return "redirect:/error";
    }

    @GetMapping(value = "/verify/{shareId}/{password}")
    @ResponseBody
    public String shareVerify(@PathVariable Long shareId, @PathVariable String password, HttpSession session) throws Exception {
        String code = shareService.shareLock(shareId, password, session);
        if (code.equals("404"))
            throw new NotFoundException("分享文件不存在");
        return code;
    }

    @GetMapping(value = "/add/{fileId}")
    @ResponseBody
    public String addShare(@PathVariable Long fileId) {
        String mesg = shareService.addShareFile(fileId);
        if (mesg.equals(UserLoginEnum.USER_NOT_IS.getKey()))
            return UserLoginEnum.USER_NOT_IS.getKey();
        if (mesg.equals(ShareEnum.SHARE_ALREADY_FILE.getKey()))
            return ShareEnum.SHARE_ALREADY_FILE.getKey();
        else if (mesg.equals(ShareEnum.SHARE_NOT_EXIT.getKey()))
            return ShareEnum.SHARE_NOT_EXIT.getKey();
        return mesg;
    }

    @GetMapping(value = "/delete/{shareId}")
    @ResponseBody
    public boolean deleteShare(@PathVariable Long shareId) {
        return shareService.deleteShare(shareId);
    }

    @GetMapping(value = "/get/{shareId}")
    public String getSahre(@PathVariable Long shareId, HttpSession session,ModelMap map) {
        String code = shareService.getSharePage(shareId, session);
        if (code.equals(ShareEnum.SHARE_NOT_EXIT.getKey()))
            return "redirect:/error";
        else if ((code.equals(ShareEnum.SHARE_FILE_NOT_VERIFY.getKey())))
            return "redirect:/share/page/" + shareId;
        map.addAttribute("share",shareService.getFile(shareId));
        return "share_download";
    }

    /**
     * 分享下载
     *
     * @param shareId 分享Id
     * @return
     */
    @GetMapping(value = "/download/{shareId}")
    public ResponseEntity<byte[]> shareDownload(HttpSession session, @PathVariable Long shareId) {
        Map<String, Object> map = shareService.download(session, shareId);
        HttpHeaders headers = new HttpHeaders();
        if (map.containsKey(ShareEnum.SHARE_FILE_NOT_VERIFY.getKey())){
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(ShareEnum.SHARE_FILE_NOT_VERIFY.getKey().getBytes());
        }
        if (map.containsKey(ShareEnum.SHARE_NOT_EXIT.getKey()))
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(ShareEnum.SHARE_NOT_EXIT.getKey().getBytes());;
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
}
