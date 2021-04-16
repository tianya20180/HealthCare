package wx.poj;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/*
* 用户实体类
* */
@Data
@ToString
@TableName("user")
public class User {
    private Integer id;
    @TableField("user_name")
    private String userName;
    private String password;
    private String phone;
    @TableField("create_time")
    private String createTime;
    private String avatar;
    private Integer money;
    @TableField("follow_size")
    private Integer followSize;
    private Integer status;
    @TableField("identity")
    private Integer identity;//0 用户 1医生
}
