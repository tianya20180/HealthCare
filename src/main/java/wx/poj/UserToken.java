package wx.poj;

import org.apache.shiro.authc.UsernamePasswordToken;
import wx.enums.VirtualType;

public class UserToken extends UsernamePasswordToken {
    private VirtualType virtualType;

    public UserToken(final String username, final String password, VirtualType virtualType) {
        super(username, password);
        this.virtualType = virtualType;
    }

    public VirtualType getVirtualType() {
        return virtualType;
    }

    public void setVirtualType(VirtualType virtualType) {
        this.virtualType = virtualType;
    }
}
