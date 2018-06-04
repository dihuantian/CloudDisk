package com.cloud.disk.web;

import org.apache.commons.fileupload.FileUploadBase;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/19
 * Time: 2:12
 */
@ControllerAdvice
public class UnificationExceptionController {

    @ExceptionHandler(value = {MaxUploadSizeExceededException.class, FileUploadBase.SizeLimitExceededException.class})
    @ResponseBody
    public String maxUploadSizeExceededException() {
        return "";
    }
}
