package wx.poj;

import lombok.Data;

@Data
public class LoginUser {
    private String phone;
    private String password;
    private Integer identity;
    private String captchaCode;
}
