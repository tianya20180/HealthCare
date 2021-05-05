package wx.poj;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("message_list")
public class MessageList {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("unread_count")
    private Integer unreadCount;
    @TableField("avatar_url")
    private String avatarUrl;
    @TableField("user_name")
    private String userName;
    @TableField("create_time")
    private String createTime;
    @TableField("msg_id")
    private String msgId;
    @TableField("doctor_id")
    private Integer doctorId;
    @TableField("user_id")
    private Integer userId;
    @TableField("words")
    private String[]words;
    @TableField("type")
    private Integer type;//0:user 到doctor 1：doctor到user

}
