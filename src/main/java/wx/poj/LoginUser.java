package wx.poj;

import lombok.Data;

@Data
public class LoginUser {
    private String userName;
    private String password;
    private Integer identity;
}
