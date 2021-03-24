package wx.poj;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("order")
public class Order {
    private Integer id;
    private String orderId;
    private Integer userId;
    private Integer doctorId;
    private String createTime;
    private Integer money;
    private Integer status;
}
