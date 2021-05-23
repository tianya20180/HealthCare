package wx.poj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/*
* 药品实体类
* @Author wangxi
* */
@Data
@TableName("drug")
public class Drug {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("drug_name")
    private String drugName;//药品名称
    @TableField("create_time")
    private String createTime;//创建时间
    private String way;//使用方法
    @TableField("prescription_id")
    private Integer prescriptionId;
    private String drugId;//批准文号
    private String notice;//注意事项
    private String type;//药品类型
    private String storage;//存储方法
    private String taboo;//禁忌
    private String effect;//适应症
    private String brand;//品牌
    private String adverseReactions;//不良反应


}
