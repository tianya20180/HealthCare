package wx.config;

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
import wx.poj.Doctor;
import wx.poj.User;
import wx.service.AdminService;
import wx.service.DoctorService;
import wx.service.UserService;

import javax.print.Doc;
import java.util.HashSet;
import java.util.Set;

public class AdminRelam extends AuthorizingRealm {


    @Autowired
    private AdminService adminService;



    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Set<String> roleNames = new HashSet<String>();
        roleNames.add("admin");//添加角色
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
        String password= DigestUtils.md5DigestAsHex(userPwd.getBytes());
        Admin admin= adminService.checkMapper(userName,password);
        if (admin == null) {
            throw new AccountException("用户名或者密码错误");
        }
        Object principal = admin;
        // （2）credentials：密码
        Object credentials = admin.getPassword();
        // （3）realmName：当前realm对象的name，调用父类的getName()方法即可
        String realmName = getName();
        // （4）盐值：取用户信息中唯一的字段来生成盐值，避免由于两个用户原始密码相同，加密后的密码也相同
        ByteSource credentialsSalt = ByteSource.Util.bytes(userName);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt,
                realmName);
        return info;
    }
}
