package com.cloud.disk.config.upload;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/15
 * Time: 16:06
 */
@Component
public class ProgressEntity implements Serializable {
    /**
     * 到目前为止读取文件的比特数
     */
    private long pBytesRead = 0L;
    /**
     * 文件总大小
     */
    private long pContentLength = 0L;
    /**
     * 目前正在读取第几个文件
     */
    private int pItems;

    public long getpBytesRead() {
        return pBytesRead;
    }

    public void setpBytesRead(long pBytesRead) {
        this.pBytesRead = pBytesRead;
    }

    public long getpContentLength() {
        return pContentLength;
    }

    public void setpContentLength(long pContentLength) {
        this.pContentLength = pContentLength;
    }

    public int getpItems() {
        return pItems;
    }

    public void setpItems(int pItems) {
        this.pItems = pItems;
    }

    @Override
    public String toString() {
        return "ProgressEntity{" +
                "pBytesRead=" + pBytesRead +
                ", pContentLength=" + pContentLength +
                ", pItems=" + pItems +
                '}';
    }
}
