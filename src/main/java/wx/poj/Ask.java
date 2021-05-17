package wx.poj;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("ask")
public class Ask {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("create_time")
    private String createTime;
    @TableField("user_id")
    private Integer userId;
    @TableField("doctor_id")
    private Integer doctorId;
    @TableField("order_id")
    private String orderId;
    private Integer status;//0 问诊结束 1问诊中
}
