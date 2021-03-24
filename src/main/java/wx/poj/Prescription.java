package wx.poj;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import java.util.*;

/*
* 处方单实体类
* @Author wangxi
* */

@Data
@ToString
@TableName("prescription")
public class Prescription {
    private Integer id;
    @TableField("doctor_id")
    private Integer doctorId;
    @TableField("user_id")
    private Integer userId;
    @TableField("create_time")
    private String createTime;
    private List<Drug>drugList;
}
