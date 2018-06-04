package com.cloud.disk.service;

import com.cloud.disk.domain.File;
import com.cloud.disk.domain.RecycleBin;
import com.cloud.disk.domain.Share;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/6/2
 * Time: 11:32
 */
public interface ShareService {

    boolean shareExit(Long shareId);

    String shareLock(Long shareId, String password, HttpSession session);

    String addShareFile(Long fileId);

    boolean deleteShare(Long shareId);

    Map<File, Share> getFileShareMap();

    String getSharePage(Long shareId, HttpSession session);

    String getThoughVerifyPassword(HttpSession session, Long shareId);

    Map<String, Object> download(HttpSession session, long shareId);

    Share getFile(Long shareId);
}
