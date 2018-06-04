package com.cloud.disk.web.controller;

import com.cloud.disk.domain.User;
import com.cloud.disk.service.LoginService;
import com.cloud.disk.situation.UserLoginEnum;
import com.cloud.disk.unit.VerificationCodeUnit;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/9
 * Time: 22:09
 */
@Controller
@RequestMapping(value = "/security")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 返回登录页面
     *
     * @return
     */
    @GetMapping(value = "/page/login")
    public String login() {
        return "login";
    }

    /**
     * 返回注册页面
     *
     * @return
     */
    @GetMapping(value = "/page/register")
    public String register() {
        return "register";
    }

    @GetMapping(value = "/page/unauthorized")
    public String unauthorized() {
        return "401";
    }

    /**
     * 根据邮箱获取密码页面
     *
     * @return
     */
    @GetMapping(value = "/page/getpass")
    public String getPassword() {
        return "getpass";
    }

    @GetMapping(value = "/page/verification_code")
    public ResponseEntity<byte[]> verificationCode(HttpSession session) throws IOException {
        Map<String, Object> map = VerificationCodeUnit.createImage();
        session.setAttribute("login_code", map.get("login_code"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<byte[]>((byte[]) map.get("codeImage"), headers, HttpStatus.CREATED);
    }

    /**
     * 登录Action
     *
     * @param email
     * @param password
     * @return
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public String login(HttpSession session, String email, String password, String code) {
        return loginService.verifyLogin(session, email, password, code);
    }

    /**
     * 注册Action
     *
     * @param password
     * @param email
     * @return
     */
    @PostMapping(value = "/register")
    @ResponseBody
    public String register(HttpSession session, String password, String email, String code) {
        return loginService.register(session, password, email, code);
    }

}
