package wx.poj;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/*
* 药品实体类
* @Author wangxi
* */
@Data
@TableName("drug")
public class Drug {
    private Integer id;
    @TableField("drug_name")
    private String drugName;//药品名称
    @TableField("create_time")
    private String createTime;
    private Integer sum;//药品总量
    private Integer times;//一天几次
    private String way;//药品使用方法
    @TableField("prescription_id")
    private Integer prescriptionId;
}
