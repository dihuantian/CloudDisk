package com.cloud.disk.situation;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/28
 * Time: 8:20
 */
public enum ClassificationEnum {
    MUSIC("MUSIC", "音乐"),
    IMAGE("IMAGE", "图片"),
    VIDEO("VIDEO", "视频"),
    DOCUMENT("DOCUMENT", "文档"),
    OTHER("OTHER", "其他");
    private String key;
    private String message;

    ClassificationEnum(String key, String message) {
        this.key = key;
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
