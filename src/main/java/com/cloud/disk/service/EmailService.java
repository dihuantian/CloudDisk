package com.cloud.disk.service;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/4/3
 * Time: 20:37
 */
public interface EmailService {

    String sendEmailVerification(String purpose, HttpSession session);

    long emailCodeTimeout(HttpSession session);
}
