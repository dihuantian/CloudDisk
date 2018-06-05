package com.cloud.disk.config.shiro;

import com.cloud.disk.domain.User;
import com.cloud.disk.repository.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/9
 * Time: 21:40
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionDAO sessionDAO;

    /**
     * 用户授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 用户认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String email = (String) authenticationToken.getPrincipal();
        if (isLogin(email))
            throw new ConcurrentAccessException();
        User user = userRepository.findByEmail(email);
        if (user != null) {
            String password = user.getPassword();
            String salt = user.getSalt();
            user.setPassword(null);
            user.setSalt(null);
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, password, ByteSource.Util.bytes(salt), getName());
            return authenticationInfo;
        }
        throw new UnknownAccountException();
    }

    /**
     * 查询用户是否已经登录过了
     * DefaultSubjectContext存储Shiro Session的Key信息
     *
     * @param email
     * @return
     */
    private boolean isLogin(String email) {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            Collection<Session> sessions = sessionDAO.getActiveSessions();
            for (Session session : sessions) {
                User user = (User) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                if (user != null) {
                    if (user.getEmail().equals(email))
                        return true;
                }
            }
        }
        return false;
    }
}
