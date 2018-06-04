package com.cloud.disk.service.impl;

import com.cloud.disk.service.EmailService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/4/3
 * Time: 20:37
 */
@Service
public class EmailServiceImpl implements EmailService {

    private @Value("${spring.mail.username}")
    String sendEmail;

    public SimpleMailMessage mailMessage;

    @Autowired
    public JavaMailSender javaMailSender;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String sendEmailVerification(String purpose, HttpSession session) {
        String email_code = (String) redisTemplate.opsForValue().get(session.getId() + "email_code");
        if (email_code != null) {
            return redisTemplate.getExpire(session.getId() + "email_code", TimeUnit.SECONDS).toString();
        }
        String code = RandomStringUtils.randomAlphabetic(6);
        /*实例化一个(简单的邮件消息)simpleMailMessage*/
        mailMessage = new SimpleMailMessage();
        /*设置收件人*/
        mailMessage.setTo(purpose);
        /*设置发件人*/
        mailMessage.setFrom(sendEmail);
        /*设置邮件主题*/
        mailMessage.setSubject("Email verification code");
        /*设置邮件内容*/
        mailMessage.setText(code);
        /*标志邮件的附件属性*/
        /*将创建的邮件消息体交由MimeMessageHelper管理*/
        try {
            javaMailSender.send(mailMessage);
            redisTemplate.opsForValue().set(session.getId() + "email_code", code, 30, TimeUnit.SECONDS);
            session.setAttribute(session.getId() + "email_code", code);
            return "true";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false";
    }

    @Override
    public long emailCodeTimeout(HttpSession session) {
        return redisTemplate.getExpire(session.getId() + "email_code", TimeUnit.SECONDS);
    }
}
