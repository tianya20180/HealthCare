package wx.poj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("my_order")
public class Order {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("order_id")
    private String orderId;
    @TableField("user_id")
    private Integer userId;
    @TableField("doctor_id")
    private Integer doctorId;
    @TableField("create_time")
    private String createTime;
    @TableField("money")
    private Integer money;
    private Integer status;
}
