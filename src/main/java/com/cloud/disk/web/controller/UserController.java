package com.cloud.disk.web.controller;

import com.cloud.disk.service.LoginService;
import com.cloud.disk.situation.UserLoginEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/6/4
 * Time: 7:42
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private LoginService loginService;

    /**
     * 注销Action
     *
     * @return
     */
    @GetMapping(value = "/logout")
    public String logout(HttpSession session) {
        String code = loginService.logout(session);
        return code.equals(UserLoginEnum.USER_LOGOUT_SUCCESS.getKey()) ? "redirect:/security/page/login" : "redirect:/error";
    }
}
