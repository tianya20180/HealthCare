package wx.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import wx.poj.Admin;
import wx.poj.User;
import wx.service.DoctorService;
import wx.service.UserService;

import java.util.HashSet;
import java.util.Set;
@Slf4j
public class UserRealm extends AuthorizingRealm {


    @Autowired
    private UserService userService;



    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Set<String> roleNames = new HashSet<String>();
        roleNames.add("user");//添加角色
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        return info;
    }

    /**
     * 这里可以注入userService,为了方便演示，我就写死了帐号了密码
     * private UserService userService;
     * <p>
     * 获取即将需要认证的信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("-------身份认证方法--------");
        String userName = (String) authenticationToken.getPrincipal();
        String userPwd = new String((char[]) authenticationToken.getCredentials());
        //根据用户名从数据库获取密码
        log.info(userName);
        log.info(userPwd);
        String password= DigestUtils.md5DigestAsHex(userPwd.getBytes());
        User user= userService.checkUser(userName, password);
        if (user == null) {
            throw new AccountException("用户名或者密码错误");
        }
        log.info(user.toString());
        return new SimpleAuthenticationInfo(userName, userPwd,getName());
    }
}
