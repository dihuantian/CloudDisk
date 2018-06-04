package com.cloud.disk.situation;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/19
 * Time: 14:04
 */
public enum FileUploadEnum {

    FOLDER_NOT_EXIT("FOLDER_NOT_EXIT", "没有此文件夹"),
    FILE_UPLOAD_SUCCESS("FILE_UPLOAD_SUCCESS", "文件上传成功"),
    FILE_NOT_EXIT("FILE_NOT_EXIT", "此文件不存在"),
    FILE_DELETE_SUCCESS("FILE_DELETE_SUCCESS", "文件删除成功"),
    FILE_REDUCTION_SUCCESS("FILE_REDUCTION_SUCCESS", "文件还原成功");
    private String key;

    private String message;

    private FileUploadEnum(String key, String message) {
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
