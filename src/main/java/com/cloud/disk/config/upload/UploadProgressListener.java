package com.cloud.disk.config.upload;

import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/15
 * Time: 16:03
 */
@Component
public class UploadProgressListener implements ProgressListener {

    private HttpSession httpSession;

    /**
     * 设置会话
     *
     * @param httpSession 传递会话参数
     */
    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
        ProgressEntity progressEntity = new ProgressEntity();
        httpSession.setAttribute("status", progressEntity);
    }

    /**
     * 侦听上传进度
     *
     * @param pBytesRead     目前读取总大小
     * @param pContentLength 上传文件总大小
     * @param pItems         第几个上传文件
     */
    @Override
    public void update(long pBytesRead, long pContentLength, int pItems) {
        ProgressEntity status = (ProgressEntity) httpSession.getAttribute("status");
        status.setpBytesRead(pBytesRead);
        status.setpContentLength(pContentLength);
        status.setpItems(pItems);
        System.out.println("已经上传:" + pBytesRead + "  上传文件总大小:" + pContentLength + "  第" + pItems + "个文件");
    }
}
