package com.cloud.disk.web.controller;

import com.cloud.disk.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/6/4
 * Time: 7:53
 */
@Controller
@RequestMapping(value = "/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/verification/code")
    @ResponseBody
    public String sendVerificationCode(String email, HttpSession session) {
        return emailService.sendEmailVerification(email, session);
    }

    @PostMapping("/verification/timeout")
    @ResponseBody
    public long getEmailCodeTimeout(HttpSession session) {
        return emailService.emailCodeTimeout(session);
    }
}
