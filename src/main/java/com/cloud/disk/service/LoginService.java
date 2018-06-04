package com.cloud.disk.service;

import com.cloud.disk.domain.User;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/9
 * Time: 22:16
 */
public interface LoginService {
    String verifyLogin(HttpSession session, String email, String password, String code);

    String register(HttpSession session, String password, String email, String code);

    String logout(HttpSession session);

    User getCurrent();
}
