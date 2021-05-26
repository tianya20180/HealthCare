package wx.poj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("diagnosis")
public class Diagnosis {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String suggestion;
    @TableField("diagnosis")
    private String diagnosis;
    private Integer userId;
    private Integer doctorId;
    @TableField("create_time")
    private String createTime;
    @TableField(exist = false)
    private String doctorName;
}
