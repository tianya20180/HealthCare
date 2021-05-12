package wx.poj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import org.omg.PortableInterceptor.INACTIVE;
@Data
@ToString
@TableName("message")
public class Message {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("msg_id")
    private String msgId;
    @TableField("doctor_id")
    private Integer doctorId;
    @TableField("user_id")
    private Integer userId;
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
}
