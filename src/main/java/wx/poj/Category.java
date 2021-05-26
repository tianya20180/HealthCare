package wx.poj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
@Data
@ToString
@TableName("category")
public class Category {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("category_name")
    private String categoryName;
    private Integer type;
    @TableField("create_time")
    private String createTime;
    private String photo;
    private Integer level;
}
