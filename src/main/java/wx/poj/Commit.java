package wx.poj;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("commit")
public class Commit {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("doctor_id")
    private Integer doctorId;
    @TableField("create_time")
    private String createTime;
    @TableField("user_name")
    private String userName;
    private String content;
}
