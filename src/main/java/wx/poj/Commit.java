package wx.poj;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("commit")
public class Commit {
    private Integer id;
    @TableField("user_id")
    private Integer userId;
    @TableField("doctor_id")
    private Integer doctorId;
    @TableField("create_time")
    private String createTime;
    private String content;
}
