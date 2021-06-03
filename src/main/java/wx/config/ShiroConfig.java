package wx.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import wx.poj.User;
import wx.poj.UserModularRealmAuthenticator;

import java.util.*;

@Configuration
public class ShiroConfig {

    /*
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
      filterChainDefinitionMap.put("/v1/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");

        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/user/**", "anon");
        filterChainDefinitionMap.put("/doctor/**", "anon");
        filterChainDefinitionMap.put("/article/**", "anon");


        filterChainDefinitionMap.put("/pay/**", "authc,roles[user]");
        filterChainDefinitionMap.put("/order/**", "authc,roles[user]");



        filterChainDefinitionMap.put("/admin/**", "authc,roles[admin]");


        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截 剩余的都需要认证
        filterChainDefinitionMap.put("/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;

    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        List<Realm> realms = new ArrayList<>();
        defaultSecurityManager.setAuthenticator(modularRealmAuthenticator());

        realms.add(UserRealm());
        realms.add(DoctorRelam());
        realms.add(AdminRelam());
        defaultSecurityManager.setRealms(realms);
        return defaultSecurityManager;
    }

    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator(){
        //自己重写的ModularRealmAuthenticator
        UserModularRealmAuthenticator modularRealmAuthenticator = new UserModularRealmAuthenticator();
        AtLeastOneSuccessfulStrategy strategy = new AtLeastOneSuccessfulStrategy();
        modularRealmAuthenticator.setAuthenticationStrategy(strategy);
        return modularRealmAuthenticator;
    }


    @Bean
    public UserRealm UserRealm() {
        UserRealm customRealm = new UserRealm();
        return customRealm;
    }
    @Bean
    public DoctorRelam DoctorRelam() {
        DoctorRelam doctorRelam = new DoctorRelam();
        return doctorRelam;
    }
    @Bean
    public AdminRelam AdminRelam() {
        AdminRelam adminRelam = new AdminRelam();
        return adminRelam;
    }
*/

}
