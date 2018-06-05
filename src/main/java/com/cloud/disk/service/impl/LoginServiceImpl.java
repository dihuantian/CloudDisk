package com.cloud.disk.service.impl;

import cn.izern.sequence.Sequence;
import com.cloud.disk.domain.User;
import com.cloud.disk.domain.UserInfo;
import com.cloud.disk.repository.UserInfoRepository;
import com.cloud.disk.repository.UserRepository;
import com.cloud.disk.service.LoginService;
import com.cloud.disk.situation.UserLoginEnum;
import com.cloud.disk.unit.UserUnit;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/9
 * Time: 22:16
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    /**
     * 登录服务,不允许已登录用户重新登录
     *
     * @param email    邮箱
     * @param password 密码
     * @param code     验证码
     * @return
     */
    @Override
    public String verifyLogin(HttpSession session, String email, String password, String code) {
        System.out.println("验证码:" + session.getAttribute("login_code"));
        Subject subject = SecurityUtils.getSubject();
        if (!((String) session.getAttribute("login_code")).equals(code))
            return UserLoginEnum.USER_LOGIN_CODE_ERROR.getKey();
        if (subject.isAuthenticated()) {
            return UserLoginEnum.USER_LOGIN_STATUS.getKey();//已经登录用户返回错误代码
        } else {
            UsernamePasswordToken token = new UsernamePasswordToken(email, password);
            try {
                subject.login(token);
                subject.getSession().setAttribute("login_status", true);
                return UserLoginEnum.USER_LOGIN_SUCCESS.getKey();
            } catch (IncorrectCredentialsException e) {
                return UserLoginEnum.USER_LOGIN_PASS_ERROR.getKey();
            } catch (UnknownAccountException e) {
                return UserLoginEnum.USER_LOGIN_USER_ERROR.getKey();
            }catch (ConcurrentAccessException e){
                return UserLoginEnum.USER_LOGIN_STATUS.getKey();
            }
        }
    }

    /**
     * 注册服务,不允许已登录用户进行注册服务
     *
     * @param password 密码
     * @param email    邮箱
     * @return
     */
    @Override
    public String register(HttpSession session, String password, String email, String code) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated())
            return UserLoginEnum.USER_LOGIN_STATUS.getKey();
        ;//已经登录用户直接返回错误代码
        if (!((String) session.getAttribute(session.getId() + "email_code")).equals(code) || code == null)
            return UserLoginEnum.REGISTER_CODE_ERROR.getKey();
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return UserLoginEnum.REGISTER_EMAIL_EXIT.getKey();//表示用户名已经存在
        } else {
            session.removeAttribute(session.getId() + "email_code");
            Sequence sequence = new Sequence();
            user = new User();
            user.setUserId(sequence.nextId());
            user.setSalt(UserUnit.getSalt());
            user.setPassword(UserUnit.passwordEncryption(password, user.getSalt()));
            user.setEmail(email);
            UserInfo userInfo = new UserInfo();
            userInfo.setSex(true);
            user.setUserInfo(userInfo);
            userInfo.setUserInfoId(sequence.nextId());
            userInfo = userInfoRepository.save(userInfo);
            user = userRepository.save(user);//封装注册信息,并将注册信息保存到数据库中
            return UserLoginEnum.REGISTER_SUCCESS.getKey();
        }
    }

    /**
     * 登录用户注销
     *
     * @return
     */
    @Override
    public String logout(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            session.removeAttribute("login_status");
            subject.logout();
            return UserLoginEnum.USER_LOGOUT_SUCCESS.getKey();
        }
        return null;//用户并没有登录,返回错误代码
    }

    /**
     * 返回当前登陆用户
     *
     * @return
     */
    @Override
    public User getCurrent() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated())
            return (User) subject.getPrincipal();
        return null;
    }
}
