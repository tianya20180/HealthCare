package wx.poj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutMessage {
    private String fromId;

    private String content;

    private String avatar;

    private String time;

    private String orderId;

    private Integer type;

}
