package wx.poj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@TableName("message")
public class InMessage {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("msg_id")
    private String msgId;
    @TableField("from_id")
    private Integer fromId;
    @TableField("to_id")
    private Integer toId;
    @TableField("avatar")
    private String avatar;
    @TableField("is_read")
    private boolean isRead;
    @TableField("content")
    private String content;
    @TableField("content_type")
    private Integer contentType;
    @TableField("send_type")
    private Integer sendType;//0代表患者 1代表医生
    @TableField("create_time")
    private String createTime;
    @TableField("order_id")
    private String orderId;

    public InMessage(String content){
        this.content=content;
    }

}
