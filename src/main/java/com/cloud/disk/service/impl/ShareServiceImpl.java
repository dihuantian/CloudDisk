package com.cloud.disk.service.impl;

import cn.izern.sequence.Sequence;
import com.cloud.disk.domain.File;
import com.cloud.disk.domain.RecycleBin;
import com.cloud.disk.domain.Share;
import com.cloud.disk.domain.User;
import com.cloud.disk.repository.FileRepository;
import com.cloud.disk.repository.ShareRepository;
import com.cloud.disk.service.FileOperationService;
import com.cloud.disk.service.ShareService;
import com.cloud.disk.situation.ShareEnum;
import com.cloud.disk.situation.UserLoginEnum;
import com.cloud.disk.unit.UserUnit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/6/2
 * Time: 11:32
 */
@Service
public class ShareServiceImpl implements ShareService {

    @Autowired
    private ShareRepository shareRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileOperationService fileOperationService;

    private @Value("${web.site.address}")
    String address;

    /**
     * 判断此分享是否存在
     *
     * @param shareId
     * @return
     */
    @Override
    public boolean shareExit(Long shareId) {
        if (!String.valueOf(shareId).trim().equals("")) {
            Optional<Share> optionalShare = shareRepository.findById(shareId);
            if (optionalShare.isPresent()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断分享加密文件是否经过密码验证,已经经过则验证则直接跳转到指定分享页面,
     * 未经过验证或者验证错误直接跳转到分享锁页面或提示验证密码错误
     *
     * @param shareId
     * @param password
     * @return
     */
    @Override
    public String shareLock(Long shareId, String password, HttpSession session) {
        String lockPassword = getThoughVerifyPassword(session, shareId);
        if (lockPassword != null) {
            //已经经过验证
            return ShareEnum.SHARE_VERIFY_THROUGH.getKey();
        } else {
            Optional<Share> optionalShare = shareRepository.findById(shareId);
            if (optionalShare.isPresent()) {
                Share share = optionalShare.get();
                if (password.equals(share.getPassword())) {
                    redisTemplate.opsForValue().set(session.getId() + String.valueOf(shareId) + "share_lock", share.getPassword(), 3, TimeUnit.MINUTES);
                    //跳转到指定的分享文件页面
                    return ShareEnum.SHARE_VERIFY_THROUGH.getKey();
                } else {
                    //验证密码错误提示
                    return ShareEnum.SHARE_PASSWORD_ERROR.getKey();
                }
            }
        }
        //此分享文件存在
        return ShareEnum.SHARE_NOT_EXIT.getKey();
    }

    /**
     * 添加分享文件
     *
     * @param fileId
     * @return 返回分享文件链接地址和分享文件提取密码
     */
    @Override
    public String addShareFile(Long fileId) {
        Optional<File> file = fileRepository.findById(fileId);
        if (file.isPresent()) {
            User user = getCurrentUser();
            if (!user.getUserId().equals(file.get().getUser().getUserId()))
                return UserLoginEnum.USER_NOT_IS.getKey();
            Share share = shareRepository.findAllByFile(file.get());
            if (share != null) {
                //表明文件已经分享了
                return ShareEnum.SHARE_ALREADY_FILE.getKey();
            }
            share = new Share();
            Sequence sequence = new Sequence();
            file.get().setShare(true);
            share.setFile(file.get());
            share.setPassword(UserUnit.getSalt());
            share.setShareDateTime(new Date());
            share.setShareId(sequence.nextId());
            share.setNumber(0);
            share.setSharePath(address + "/share/get/" + share.getShareId());
            shareRepository.save(share);
            GsonBuilder builder = new GsonBuilder();
            return builder.excludeFieldsWithoutExposeAnnotation().create().toJson(share);//分享成功
        }
        //表明此需要分享文件不存在
        return ShareEnum.SHARE_NOT_EXIT.getKey();
    }

    /**
     * 关闭文件的分享
     *
     * @param shareId
     * @return
     */
    @Override
    public boolean deleteShare(Long shareId) {
        Optional<Share> optionalShare = shareRepository.findById(shareId);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (optionalShare.isPresent()) {
            Share share = optionalShare.get();
            if (!getCurrentUser().getUserId().equals(user.getUserId()))
                return false;
            shareRepository.delete(share);
            return true;
        }
        return false;
    }

    /**
     * 获取此用户所有的分享文件
     *
     * @return
     */
    @Override
    public Map<File, Share> getFileShareMap() {
        User user = getCurrentUser();
        if (user != null) {
            List<File> files = fileRepository.findAllByUser(user).stream().filter(u -> u.getShare()).collect(Collectors.toList());
            if (files.size() > 0) {
                List<Share> shares = shareRepository.findAllByFileIn(files);
                Map<File, Share> map = new LinkedHashMap<>();
                for (Share share : shares) {
                    map.put(share.getFile(), share);
                }
                return map;
            }
        }
        return null;
    }

    /**
     * 访问分享文件
     *
     * @param shareId
     * @param session
     * @return
     */
    @Override
    public String getSharePage(Long shareId, HttpSession session) {
        String lockPassword = getThoughVerifyPassword(session, shareId);
        if (shareExit(shareId) && lockPassword != null) {
            return ShareEnum.SHARE_VERIFY_THROUGH.getKey();
        } else if (lockPassword == null) {
            return ShareEnum.SHARE_FILE_NOT_VERIFY.getKey();
        }
        return ShareEnum.SHARE_NOT_EXIT.getKey();
    }

    @Override
    public String getThoughVerifyPassword(HttpSession session, Long shareId) {
        String lockPassword = (String) redisTemplate.opsForValue().get(session.getId() + String.valueOf(shareId) + "share_lock");
        return lockPassword != null ? lockPassword : null;
    }

    private User getCurrentUser() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user;
    }

    @Override
    public Share getFile(Long shareId) {
        Share share = shareRepository.findById(shareId).get();
        if (share!=null)
            return share;
        return null;
    }

    @Override
    public Map<String, Object> download(HttpSession session, long shareId) {
        Map<String, Object> mapFile = null;
        if (getThoughVerifyPassword(session, shareId) != null) {
            if (shareExit(shareId)) {
                Share share = shareRepository.findById(shareId).get();
                File file = share.getFile();
                mapFile = fileOperationService.getFastFDFSFile(file.getFileId());
                if (mapFile != null) {
                    mapFile.put("file", file);
                    return mapFile;
                }
            }
            mapFile = new LinkedHashMap<>();
            mapFile.put(ShareEnum.SHARE_NOT_EXIT.getKey(), ShareEnum.SHARE_NOT_EXIT.getKey());
        }
        mapFile.put(ShareEnum.SHARE_FILE_NOT_VERIFY.getKey(), ShareEnum.SHARE_FILE_NOT_VERIFY.getKey());
        return mapFile;
    }
}
