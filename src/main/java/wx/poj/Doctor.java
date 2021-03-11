package wx.poj;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/*
*
* 医生实体类
* */
@Data
@TableName("doctor")
public class Doctor {
    private Integer id;
    @TableField("user_name")
    private String userName;
    private String password;
    private String phone;
    private String address;
    @TableField("card_id")
    private String cardId;//身份证
    private String avatar;
    @TableField("create_time")
    private String createTime;

}
