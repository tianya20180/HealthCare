package wx.poj;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("union")
public class Union {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("drug_id")
    private Integer drugId;
    @TableField("prescription_id")
    private Integer prescriptionId;
    public Union(Integer drugId,Integer prescriptionId){
        this.drugId=drugId;
        this.prescriptionId=prescriptionId;
    }
}
