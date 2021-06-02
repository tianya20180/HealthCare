package wx.poj;

import org.apache.shiro.authc.UsernamePasswordToken;
import wx.enums.VirtualType;

public class UserToken extends UsernamePasswordToken {
    private String loginType;

    public UserToken(final String username, final String password, String loginType) {
        super(username, password);
        this.loginType = loginType;
    }

    public String getLoginType() {
        return loginType;
    }
    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

}
