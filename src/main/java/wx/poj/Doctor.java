package wx.poj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/*
*
* 医生实体类
* */
@Data
@ToString
@TableName("doctor")
public class Doctor {
    @TableId(value = "id",type = IdType.AUTO)
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
    @TableField("working_years")
    private Integer workYears;
    @TableField("des")
    private String des;
    @TableField("hospital")
    private String hospital;
    @TableField("specialty")
    private String specialty;
    @TableField("count")
    private Integer count;
    @TableField("order_money")
    private Integer orderMoney;
    private double money;
    @TableField("status")
    private Integer status;
    private Integer identity;//0 用户 1医生
    private Integer category;
    @TableField(exist = false)
    private String categoryName;


}
