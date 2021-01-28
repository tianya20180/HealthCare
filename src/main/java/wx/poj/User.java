package wx.poj;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/*
* 用户实体类
* */
@Data
@TableName("user")
public class User {
    private Integer id;
    @TableField("user_name")
    private String userName;
    private String password;
    private String phone;
}
