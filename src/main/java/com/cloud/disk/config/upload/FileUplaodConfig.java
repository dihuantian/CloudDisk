package com.cloud.disk.config.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/15
 * Time: 19:20
 */
@Configuration
public class FileUplaodConfig {

    private static final long BYTE = 1048576l;

    public @Value("${spring.servlet.multipart.max-file-size}")
    long MAX_FILE_SIZE;

    public @Value("${spring.servlet.multipart.max-request-size}")
    long MAX_REQUEST_SIZE;

    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        CustomMultipartResolver customMultipartResolver = new CustomMultipartResolver();
        customMultipartResolver.setDefaultEncoding("UTF-8");
        customMultipartResolver.setResolveLazily(true);
        customMultipartResolver.setMaxUploadSize(MAX_FILE_SIZE * BYTE);
        customMultipartResolver.setMaxUploadSizePerFile(MAX_REQUEST_SIZE * BYTE);
        return customMultipartResolver;
    }
}
